package com.example.mhchatbot

import androidx.compose.runtime.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mhchatbot.ui.theme.backgroundColor

//import coil.compose.AsyncImage

@Composable
fun VideoSelfHelpScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Today's Recommendations Section
            RecommendationSection(
                title = "Today's Recommendations",
                author = "By Mindful Moments",
                videoTitle = "10Minutes Anxiety Relief Practice",
                backgroundColor = Color(0xFFEEE6DB)
            )

            // Last Week's Recommendations Section
            RecommendationSection(
                title = "Last Week Recommendations",
                author = "By Mindful Moments",
                videoTitle = "10Minutes Anxiety Relief Practice",
                backgroundColor = Color(0xFFE6E9F5)
            )
        }
    }
}

@Composable
fun RecommendationSection(
    title: String,
    author: String,
    videoTitle: String,
    backgroundColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )

        Text(
            text = author,
            fontSize = 14.sp,
            color = Color(0xFF666666),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Video thumbnail with YouTube-like player
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFF9B4E46))
        ) {
            // Placeholder for the video thumbnail
            // In a real app, you would use AsyncImage with Coil or Glide
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Meditation title
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Meditation to",
                        fontSize = 14.sp,
                        color = Color(0xFF8FD14F),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "CALM YOUR ANXIOUS",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "THOUGHTS",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                // Circles decoration (simplified)
                // In a real app, you would draw these circles in a more precise way
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color.White.copy(alpha = 0.7f), RoundedCornerShape(6.dp))
                    )
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    )
                }

                // YouTube player controls overlay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .align(Alignment.BottomCenter)
                        .background(Color.Black.copy(alpha = 0.6f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Play/Pause icon placeholder
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color.Transparent)
                        )

                        // Progress bar
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .padding(horizontal = 8.dp)
                                .background(Color.Gray)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(4.dp)
                                    .background(Color.Red)
                            )
                        }

                        // Time indicator
                        Text(
                            text = "3:45 / 10:00",
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Video title
        Text(
            text = videoTitle,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
