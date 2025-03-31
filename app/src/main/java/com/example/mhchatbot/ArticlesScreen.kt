package com.example.mhchatbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mhchatbot.ui.theme.backgroundColor


// Data class for mental health cards
data class MentalHealthCard(
    val id: Int,
    val title: String,
    var description: String,
    val url:String,
    val gradientColors: List<Color>
)

@Composable
fun ArticlesScreen() {
    val sharedScreenViewModel = SharedScreenViewModel()
    // In-memory storage (non-persistent, will be destroyed when app is closed)
    val cards = remember { mutableStateListOf(
        MentalHealthCard(
            1,
            "Reduce Anxiety",
            "Anxiety is related to the stress response, which can be beneficial and useful. It makes you aware of danger, motivates you to stay organized and prepared, and helps you calculate risks..........",
            url = "https://www.healthline.com/health/natural-ways-to-reduce-anxiety",
            listOf(Color(0xFF2E5D6C), Color(0xFF1F3A44))
        ),
        MentalHealthCard(
            2,
            "Anxiety Exercise",
            "Managing anxiety is a crucial aspect of maintaining mental health, and it's important for you to equip yourself with effective tools and strategies........",
            url = "https://www.choosingtherapy.com/anxiety-exercises/",
            listOf(Color(0xFF5E3A69), Color(0xFF2F1D34))
        ),
        MentalHealthCard(
            3,
            "Depression",
            "When you have depression or anxiety, exercise often seems like the last thing you want to do..........",
            url ="https://www.mayoclinic.org/diseases-conditions/depression/in-depth/depression-and-exercise/art-20046495",
            listOf(Color(0xFF7A8F5F), Color(0xFF3F4830))
        )
    )}

    var nextId = remember { mutableStateOf(4) }
    var showAddDialog = remember { mutableStateOf(false) }
    var editingCard = remember { mutableStateOf<MentalHealthCard?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor) // Light blue background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cards list
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(cards) { card ->
                    MentalHealthCardItem(
                        card = card,
                        url=card.url
                    )
                }
            }

            // "More adding soon..." text
            Text(
                text = "More adding soon..",
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }


}

@Composable
fun MentalHealthCardItem(
    card: MentalHealthCard,
    url: String,
    modifier: Modifier = Modifier
) {
    val sharedScreenViewModel = SharedScreenViewModel()
    val context = LocalContext.current
    Box(
        modifier = modifier
            .height(120.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(card.gradientColors)
            )
            .clickable { sharedScreenViewModel.openBrowser(context = context,url) }
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = card.title,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = card.description,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }
}


