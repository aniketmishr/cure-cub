package com.example.mhchatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mhchatbot.ui.theme.backgroundColor


// Enhanced Audio Card Composable that includes play/pause functionality
@Composable
fun AudioCardWithControls(
    title: String,
    description: String,
    audioId: String,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier,
    viewModel: AudioPlayerViewModel = viewModel(),
    showTapToPlay: Boolean = false
) {
    val context = LocalContext.current
    val isPlaying = viewModel.currentlyPlayingId.value == audioId

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.linearGradient(colors = gradientColors)
            )
            .clickable { viewModel.playAudio(context, audioId) }
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Audio wave icon
                Icon(
                    painter = painterResource(id = R.drawable.wave),
                    contentDescription = "Audio Wave",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )

                if (showTapToPlay && !isPlaying) {
                    Text(
                        text = "TAP TO PLAY",
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                } else if (isPlaying) {
                    Text(
                        text = "PLAYING",
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(if (showTapToPlay) 20.dp else 8.dp))

            Text(
                text = title,
                fontSize = if (showTapToPlay) 24.sp else 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f),
                lineHeight = 20.sp
            )
        }

        // Play/Pause Button (optional, for visual feedback)
        if (isPlaying) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Pause",
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

// Updated RelaxingAudiosScreen that uses the AudioPlayerViewModel
@Composable
fun RelaxingAudiosScreen(
    onBackClick: () -> Unit,
    viewModel: AudioPlayerViewModel
) {

    val stopAudio = rememberUpdatedState(viewModel::stopAudio)

    // DisposableEffect keyed on Unit will execute once when entering composition
    // and clean up when leaving composition
    DisposableEffect(Unit) {
        onDispose {
            stopAudio.value()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Rainfall Lofi Card
            AudioCardWithControls(
                title = "RAINFALL LOFI",
                description = "Release the day's weight with every falling drop. Let the rain cleanse your mind and spirit. Find comfort in nature's calming melody. A gentle reminder to breathe and let go.",
                audioId = "rainfall_lofi",
                gradientColors = listOf(Color(0xFF2C5364), Color(0xFF203A43), Color(0xFF8E2E2E)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                viewModel = viewModel,
                showTapToPlay = true
            )

            // Two cards in a row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Seashore Card
                AudioCardWithControls(
                    title = "SeaShore",
                    description = "Release the day's weight with every falling drop. Let the rain cleanse your mind and spirit.",
                    audioId = "seashore",
                    gradientColors = listOf(Color(0xFF614385), Color(0xFF516395)),
                    modifier = Modifier
                        .weight(1f)
                        .height(200.dp),
                    viewModel = viewModel
                )

                // Fire Camping Card
                AudioCardWithControls(
                    title = "Fire Camping",
                    description = "Release the day's weight with every falling drop. Let the rain cleanse your mind and spirit.",
                    audioId = "fire_camping",
                    gradientColors = listOf(Color(0xFF606C38), Color(0xFF283618)),
                    modifier = Modifier
                        .weight(1f)
                        .height(200.dp),
                    viewModel = viewModel
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "More adding soon..",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}