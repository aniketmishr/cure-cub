//package com.example.mhchatbot
//
//import android.content.Context
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
//private val Context.dataStore by preferencesDataStore(name = "user_prefs")
//
//class StoreUserInfo(private val context: Context) {
//    companion object {
//        private val USER_NAME_KEY = stringPreferencesKey("user_name")
//    }
//
//    val userName: Flow<String?> = context.dataStore.data.map { preferences ->
//        preferences[USER_NAME_KEY]
//    }
//
//    suspend fun saveUserInfo(name: String) {
//        context.dataStore.edit { preferences ->
//            preferences[USER_NAME_KEY] = name
//        }
//    }
//}

// 1. Create a DataStore preferences
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Extension property for Context to create a single DataStore instance
val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

// 2. Create a repository class to handle DataStore operations
class UserPreferencesRepository(private val context: Context) {

    // Define keys
    companion object {
        val USER_NAME_KEY = stringPreferencesKey("user_name")
    }

    // Get user name as a Flow
    val userName: Flow<String> = context.userPreferencesDataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }

    // Save user name
    suspend fun saveUserName(name: String) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }

    // Clear user name
    suspend fun clearUserName() {
        context.userPreferencesDataStore.edit { preferences ->
            preferences.remove(USER_NAME_KEY)
        }
    }
}

// 3. Create a ViewModel to use the repository
class UserViewModel(private val repository: UserPreferencesRepository) : ViewModel() {

    // Expose user name as StateFlow
    val userName = repository.userName.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    // Save user name
    fun saveUserName(name: String) {
        viewModelScope.launch {
            repository.saveUserName(name)
        }
    }

    // Clear user name
    fun clearUserName() {
        viewModelScope.launch {
            repository.clearUserName()
        }
    }
}

// 4. Factory for ViewModel creation
class UserViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(UserPreferencesRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}