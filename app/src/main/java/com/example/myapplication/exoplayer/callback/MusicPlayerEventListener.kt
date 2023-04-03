package com.example.myapplication.exoplayer.callback

import android.app.Service
import android.app.Service.STOP_FOREGROUND_REMOVE
import android.widget.Toast
import com.example.myapplication.exoplayer.MusicService
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player


class MusicPlayerEventListener(
    private val musicService: MusicService
) : Player.Listener {
    @Deprecated("Deprecated in Java")
    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, playbackState)
        if (playbackState == Player.STATE_READY && !playWhenReady) {
            musicService.stopForeground(STOP_FOREGROUND_REMOVE)
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        Toast.makeText(musicService, "Bilinmeyen bir hata oluştu", Toast.LENGTH_LONG).show()
    }
}