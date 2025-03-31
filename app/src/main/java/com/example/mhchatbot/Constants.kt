package com.example.mhchatbot

import androidx.compose.ui.res.painterResource

object Constants {
    val apiKey = "AIzaSyC07Lk4fuOL9dTRqBHMAb7juU6FSK_aJUs"
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Chat",
            icon = R.drawable.chat,
            route = Screen.Welcome.route
        ),
        BottomNavItem(
            label = "Self-Help",
            icon = R.drawable.selfhelp,
            route = Screen.SelfHelp.route
        ),
        BottomNavItem(
            label = "Journal",
            icon = R.drawable.journal,
            route = Screen.JournalScreen.route
        ),
        BottomNavItem(
            label = "Mind Booster",
            icon = R.drawable.mood_booster,
            route = Screen.MindBoosterScreen.route
        )
    )

}