package com.example.mhchatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mhchatbot.ui.theme.backgroundColor

@Composable
fun SelfHelpScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header text
            Text(
                text = "\"Empower yourself with our app's self-help feature, offering personalized support to navigate through tough times and find joy again.\"",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            // Grid of cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Audios card
                CategoryCard(
                    title = "Audios",
                    description = "Release the day's weight with every falling drop. Let the rain cleanse your mind and spirit. Find comfort in nature's calming melody.",
                    backgroundColor = Color(0xFF00b4d8),
                    contentColor = Color.White,
                    modifier = Modifier.weight(1f),
                    onClick = {navController.navigate(Screen.AudioScreen.route)}
                )

                // Videos card
                CategoryCard(
                    title = "Videos",
                    description = "Release the day's weight with every falling drop. Let the rain cleanse your mind and spirit. Find comfort in nature's calming melody.",
                    backgroundColor = Color(0xFF90e0ef),
                    contentColor = Color(0xFF2E4F4F),
                    modifier = Modifier.weight(1f),
                    onClick = {navController.navigate(Screen.VideoSelfHelp.route)}
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Articles card
                CategoryCard(
                    title = "Articles",
                    description = "Release the day's weight with every falling drop. Let the rain cleanse your mind and spirit. Find comfort in nature's calming melody.",
                    backgroundColor = Color(0xFF07C4EC),
                    contentColor = Color.White,
                    modifier = Modifier.weight(1f),
                    onClick = {navController.navigate(Screen.Article.route)}
                )

                // Podcast card
                CategoryCard(
                    title = "Meditation",
                    description = "Release the day's weight with every falling drop. Let the rain cleanse your mind and spirit. Find comfort in nature's calming melody.",
                    backgroundColor = Color(0xFFcaf0f8),
                    contentColor = Color(0xFF2E4F4F),
                    modifier = Modifier.weight(1f),
                    onClick = {navController.navigate(Screen.MeditationScreen.route)}
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // "More adding soon" text
            Text(
                text = "More adding soon...",
                fontSize = 14.sp,
                color = Color(0xFF2E4F4F),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun CategoryCard(
    title: String,
    description: String,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(180.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 12.sp,
                color = contentColor.copy(alpha = 0.8f),
                lineHeight = 16.sp
            )
        }
    }
}
