package com.example.mhchatbot

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mhchatbot.ui.theme.backgroundColor

@Composable
fun MindBoosterScreen() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
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
    val iconColor: Color,
    val url: String,
    val icon: Int
)

@Composable
fun GameItem(game: Game) {
    val context = LocalContext.current
    val sharedScreenViewModel = SharedScreenViewModel()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { sharedScreenViewModel.openBrowser(context, game.url) }
        ,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(game.icon), contentDescription = null)

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
            iconColor = Color.Black,
            url = "https://play.google.com/store/apps/details?id=com.balysv.loop",
            R.drawable.infinity_loop
        ),
        Game(
            title = "Energy: Anti-Stress Loops",
            developer = "Infinity Games, Ltd.",
            categories = listOf("Puzzle", "Logic", "Casual"),
            rating = 4.7f,
            size = "25 MB",
            iconColor = Color.Gray,
            url = "https://play.google.com/store/apps/details?id=com.infinitygames.loopenergy",
            R.drawable.energy_antistress
        ),
        Game(
            title = "Hex: Anxiety Relief Relax Game",
            developer = "Infinity Games, Ltd.",
            categories = listOf("Puzzle", "Logic", "Casual"),
            rating = 4.5f,
            size = "32 MB",
            iconColor = Color(0xFF6A1B9A),
            url = "https://play.google.com/store/apps/details?id=com.infinitygames.loophex",
            R.drawable.hex
        ),
        Game(
            title = "Antisquare - Relax Mini Games",
            developer = "Relax Tech",
            categories = listOf("Simulation", "Fun", "Puzzle", "Logic", "Casual"),
            rating = 4.6f,
            size = "40 MB",
            iconColor = Color(0xFFFFA000),
            url = "https://play.google.com/store/apps/details?id=com.uc.minigame.relax",
            R.drawable.anti
        )
    )
}

