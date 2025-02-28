package com.example.mhchatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MindBoosterScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6F2F8))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Header text
                Text(
                    text = "Mind Booster  Feeling down or stressed? Our Mind Booster games, curated by our team, are here to help you relax and recharge. Explore a variety of fun and engaging games designed to release stress and ease anxiety. Take a break and give your mind the boost it deserves.",
                    fontSize = 16.sp,
                    color = Color(0xFF2E4F4F),
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Suggested Games title
                Text(
                    text = "Suggested Games:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Game items
            items(getGamesList()) { game ->
                GameItem(game = game)
            }
        }
    }
}

data class Game(
    val title: String,
    val developer: String,
    val categories: List<String>,
    val rating: Float,
    val size: String,
    val isPremium: Boolean = false,
    val iconColor: Color
)

@Composable
fun GameItem(game: Game) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Game icon
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(game.iconColor),
                contentAlignment = Alignment.Center
            ) {
                // Simple icon representation based on the game type
                when {
                    game.title.contains("Infinity Loop") -> {
                        Text(
                            text = "∞",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    game.title.contains("Energy") -> {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.5f))
                        )
                    }
                    game.title.contains("Hex") -> {
                        Text(
                            text = "⬡",
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }
                    game.title.contains("Antisquare") -> {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(Color.Yellow)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Game info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = game.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Text(
                    text = "${game.developer} • ${game.categories.joinToString(" • ")}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    // Rating stars
                    Text(
                        text = game.rating.toString(),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(12.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Size
                    Text(
                        text = game.size,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    if (game.isPremium) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Premium",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

// Sample data for the games list
fun getGamesList(): List<Game> {
    return listOf(
        Game(
            title = "Infinity Loop: Relaxing Puzzle",
            developer = "Infinity Games, Ltd.",
            categories = listOf("Puzzle", "Logic", "Casual"),
            rating = 4.8f,
            size = "28 MB",
            isPremium = true,
            iconColor = Color.Black
        ),
        Game(
            title = "Energy: Anti-Stress Loops",
            developer = "Infinity Games, Ltd.",
            categories = listOf("Puzzle", "Logic", "Casual"),
            rating = 4.7f,
            size = "25 MB",
            iconColor = Color.Gray
        ),
        Game(
            title = "Hex: Anxiety Relief Relax Game",
            developer = "Infinity Games, Ltd.",
            categories = listOf("Puzzle", "Logic", "Casual"),
            rating = 4.5f,
            size = "32 MB",
            isPremium = true,
            iconColor = Color(0xFF6A1B9A)
        ),
        Game(
            title = "Antisquare - Relax Mini Games",
            developer = "Relax Tech",
            categories = listOf("Simulation", "Fun", "Puzzle", "Logic", "Casual"),
            rating = 4.6f,
            size = "40 MB",
            iconColor = Color(0xFFFFA000)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MindBoosterScreenPreview() {
    MaterialTheme {
        MindBoosterScreen()
    }
}