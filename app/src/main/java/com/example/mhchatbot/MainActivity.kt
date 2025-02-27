package com.example.mhchatbot

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mhchatbot.ui.theme.MHChatbotTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        setContent {
            MHChatbotTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding->
                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController=navController,
                        startDestination = Screen.Name.route
                    ) {
                        composable(route = Screen.Welcome.route) {
                            WelcomeScreen(navController= navController, onStartChatting = {navController.navigate(Screen.Chat.route)})
                        }
                        composable(route = Screen.Name.route) {
                            NameScreen(navController= navController, onGetStarted = {navController.navigate(Screen.Welcome.route)})
                        }
                        composable(route = Screen.Chat.route) {
                            ChatPage(Modifier, chatViewModel)
                        }

                    }

                }
            }
        }
    }
}

