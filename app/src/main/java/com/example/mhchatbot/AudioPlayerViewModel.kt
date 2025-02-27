package com.example.mhchatbot

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException


// Audio Player ViewModel
class AudioPlayerViewModel : ViewModel() {
    // Map of audio ID to resource ID
    private val audioResources = mapOf(
        "rainfall_lofi" to R.raw.rainfall_lofi,
        "seashore" to R.raw.seashore,
        "fire_camping" to R.raw.fire_camping
    )

    // Current playing state
    private var mediaPlayer: MediaPlayer? = null
    private var _currentlyPlayingId = mutableStateOf<String?>(null)
    val currentlyPlayingId: State<String?> = _currentlyPlayingId

    // Play audio function
    fun playAudio(context: Context, audioId: String) {
        viewModelScope.launch {
            // If same audio is playing, pause it
            if (_currentlyPlayingId.value == audioId) {
                stopAudio()
                return@launch
            }

            // Stop any currently playing audio
            stopAudio()

            // Get resource ID
            val resourceId = audioResources[audioId] ?: return@launch

            try {
                // Create and set up media player
                mediaPlayer = MediaPlayer.create(context, resourceId)
                mediaPlayer?.apply {
                    setOnCompletionListener {
                        _currentlyPlayingId.value = null
                        mediaPlayer = null
                    }
                    isLooping = true
                    start()
                    _currentlyPlayingId.value = audioId
                }
            } catch (e: IOException) {
                _currentlyPlayingId.value = null
                mediaPlayer = null
            }
        }
    }

    // Stop audio function
    fun stopAudio() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
        _currentlyPlayingId.value = null
    }

    // Clean up when ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        stopAudio()
    }
}