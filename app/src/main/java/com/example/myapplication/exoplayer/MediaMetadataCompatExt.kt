package com.example.myapplication.exoplayer.callback

import android.support.v4.media.MediaMetadataCompat
import com.example.myapplication.data.entities.Song

fun MediaMetadataCompat.toSong(): Song? {
    return description?.let {
        Song(
            it.mediaId ?: "",
            it.title.toString(),
            it.subtitle.toString(),
            it.mediaUri.toString(),
            it.iconUri.toString()
        )
    }
}