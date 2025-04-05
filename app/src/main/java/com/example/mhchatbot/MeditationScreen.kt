package com.example.mhchatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mhchatbot.ui.theme.backgroundColor


@Composable
fun MeditationScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottiemeditation))
    Box(modifier = Modifier.background(backgroundColor)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition,
                speed = 0.3f,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .size(400.dp)
            )
        }
    }
}