package com.example.mhchatbot




sealed class Screen(val route: String)
{
    object Name: Screen("name_screen")
    object Chat: Screen("chat_screen")
    object Welcome : Screen("welcome_screen")
}