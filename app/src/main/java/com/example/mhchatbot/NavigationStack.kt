package com.example.mhchatbot

import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable


sealed class Screen(val route: String,val topBarTitle: String)
{
    object Name: Screen("name_screen", "")
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

//NavHost(
//modifier = Modifier.padding(innerPadding),
//navController=navController,
//startDestination = Screen.Name.route
//) {
//    composable(route = Screen.Welcome.route) {
//        WelcomeScreen(navController= navController, onStartChatting = {navController.navigate(Screen.Chat.route)})
//    }
//    composable(route = Screen.Name.route) {
//        NameScreen(navController= navController, onGetStarted = {navController.navigate(Screen.Welcome.route)})
//    }
//    composable(route = Screen.Chat.route) {
//        ChatPage(Modifier, chatViewModel)
//    }
//    composable(route = Screen.AudioScreen.route) {
//        RelaxingAudiosScreen(onBackClick = {}, audioPlayerViewModel)
//    }
//
//}