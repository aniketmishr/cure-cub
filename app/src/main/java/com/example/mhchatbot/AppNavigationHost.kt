package com.example.mhchatbot

import UserViewModel
import UserViewModelFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val chatViewModel = ChatViewModel(userName)

    NavHost(
        modifier = Modifier.padding(innerPadding),
        navController = navController, startDestination = if (userName.isNotEmpty()) Screen.Welcome.route else Screen.Name.route
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                navController = navController,
                onStartChatting = { navController.navigate(Screen.Chat.route) },
                userName =userName
            )
        }
        composable(route = Screen.Name.route) {
            NameScreen(
                userViewModel = userViewModel,
                navController = navController
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