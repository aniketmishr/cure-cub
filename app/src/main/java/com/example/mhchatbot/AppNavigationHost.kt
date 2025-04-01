package com.example.mhchatbot

import UserViewModel
import UserViewModelFactory
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AppNavigationHost(innerPadding: PaddingValues, navController: NavHostController) {
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(LocalContext.current)
    )
    val audioPlayerViewModel = AudioPlayerViewModel()
    val userName by userViewModel.userName.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()
    val chatViewModel = ChatViewModel(userName)

    var showDialog by remember { mutableStateOf(false) }

    val dialogDismissToast = Toast.makeText(LocalContext.current, "Enter Name", Toast.LENGTH_SHORT)

    LaunchedEffect(userName, isLoading) {
        if (!isLoading && userName.isEmpty()) {
            showDialog = true
        }
    }

    if (showDialog) {
        NameInputDialog(
            onDismiss = {dialogDismissToast.show() },
            onConfirm = { enteredName ->
                userViewModel.saveUserName(enteredName)
                showDialog = false
            }
        )
    }

    NavHost(
        modifier = Modifier.padding(innerPadding),
        navController = navController, startDestination = Screen.Welcome.route
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                navController = navController,
                onStartChatting = { navController.navigate(Screen.Chat.route) },
                userName = userName
            )
        }
        composable(route = Screen.Chat.route) {
            ChatPage(Modifier, chatViewModel)
        }
        composable(route = Screen.AudioScreen.route) {
            RelaxingAudiosScreen(onBackClick = {}, audioPlayerViewModel)
        }
        composable(route = Screen.MeditationScreen.route) {
            MeditationScreen()
        }
        composable(route = Screen.MindBoosterScreen.route) {
            MindBoosterScreen()
        }
        composable(route = Screen.SelfHelp.route) {
            SelfHelpScreen(navController)
        }
        composable(route = Screen.VideoSelfHelp.route) {
            VideoSelfHelpScreen()
        }
        composable(route = Screen.JournalScreen.route) {
            JournalScreen()
        }
        composable(route = Screen.Article.route) {
            ArticlesScreen()
        }
    }
}

sealed class Screen(val route: String,val topBarTitle: String)
{
    object Chat: Screen("chat_screen","")
    object Welcome : Screen("welcome_screen", "Chat")
    object AudioScreen: Screen("audio_screen","Relaxing Audios")
    object MeditationScreen: Screen("meditation_screen", "Meditation")
    object MindBoosterScreen: Screen("mindbooster_screen", "Mind Booster")
    object SelfHelp: Screen("selfhelp_screen", "Self Help")
    object VideoSelfHelp: Screen("video_selfhelp","Videos")
    object JournalScreen: Screen("journal_screen","Journal")
    object Article: Screen("article_screen","Article")

}