package com.example.mhchatbot

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
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
                backgroundColor = Color(0xFFEEE6DB),
                imageRes = R.drawable.meditation_2,
                "https://youtu.be/z-IR48Mb3W0?feature=shared"
            )

            // Last Week's Recommendations Section
            RecommendationSection(
                title = "Last Week Recommendations",
                author = "By Mindful Moments",
                videoTitle = "10Minutes Anxiety Relief Practice",
                backgroundColor = Color(0xFFE6E9F5),
                imageRes = R.drawable._0_min_meditation,
                "https://youtu.be/z-IR48Mb3W0?feature=shared"
            )
        }
    }
}

@Composable
fun RecommendationSection(
    title: String,
    author: String,
    videoTitle: String,
    backgroundColor: Color,
    imageRes:Int,
    url: String
) {
    val context = LocalContext.current
    val sharedScreenViewModel = SharedScreenViewModel()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(16.dp)
            .clickable { sharedScreenViewModel.openBrowser(context, url) }
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

        Image(
            painter = painterResource(imageRes),
            contentDescription = null
        )
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
