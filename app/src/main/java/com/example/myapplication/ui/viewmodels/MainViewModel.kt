package com.example.myapplication.ui.viewmodels

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.SubscriptionCallback
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.entities.Song
import com.example.myapplication.exoplayer.*
import com.example.myapplication.other.Constants.MEDIA_ROOT_ID
import com.example.myapplication.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {

    private val _mediaItems = MutableLiveData<Resource<List<Song>>>()
    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems

    val isConnected = musicServiceConnection.isConnected
    val networkError = musicServiceConnection.networkError
    val currentPlayingSong = musicServiceConnection.currPlayingSong
    val playbackState = musicServiceConnection.playbackState

    init {
        _mediaItems.postValue(Resource.loading(null))
        musicServiceConnection.subscribe(
            MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {
                override fun onChildrenLoaded(
                    parentId: String,
                    children: MutableList<MediaBrowserCompat.MediaItem>
                ) {
                    super.onChildrenLoaded(parentId, children)
                    val items = children.map {
                        Song(
                            it.mediaId!!,
                            it.description.title.toString(),
                            it.description.subtitle.toString(),
                            it.description.mediaUri.toString(),
                            it.description.iconUri.toString()
                        )
                    }
                    _mediaItems.postValue(Resource.success(items))

                }
            })

    }

    fun skipToNextSong() {
        musicServiceConnection.transportControls.skipToNext()
    }

    fun skipToPreviosSong() {
        musicServiceConnection.transportControls.skipToPrevious()
    }

    fun seekTo(position: Long) {
        musicServiceConnection.transportControls.seekTo(position)
    }

    fun playOrToggleSong(mediaItem: Song, toggle: Boolean = false) {
        val isPrepared = playbackState.value?.isPrepared ?: false
        if (isPrepared && mediaItem.mediaId ==
            currentPlayingSong.value?.getString(METADATA_KEY_MEDIA_ID)
        ) {
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> if (toggle) musicServiceConnection.transportControls.pause()
                    playbackState.isPlayEnablea -> musicServiceConnection.transportControls.play()
                    else -> Unit
                }
            }

        } else {
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.mediaId, null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.unsubscribe(MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}

//@HiltViewModel
//class MainViewModel @Inject constructor(
//    private val musicServiceConnection: MusicServiceConnection
//) : ViewModel() {
//    private val _mediaItems = MutableLiveData<Resource<List<Song>>>()
//    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems
//
//
//    val isConnected = musicServiceConnection.isConnected
//    val networkError = musicServiceConnection.networkError
//    val curPlayingSong = musicServiceConnection.curPlayingSong
//    val playbackState = musicServiceConnection.playbackState
//
//    init {
//        _mediaItems.postValue(Resource.loading(null))
//        musicServiceConnection.subscribe(MEDIA_ROOT_ID, object : SubscriptionCallback(){
//            override fun onChildrenLoaded(
//                parentId: String,
//                children: MutableList<MediaBrowserCompat.MediaItem>
//            ) {
//                super.onChildrenLoaded(parentId, children)
//                val items = children.map {
//                    Song(
//                        it.mediaId!!,
//                        it.description.title.toString(),
//                        it.description.subtitle.toString(),
//                        it.description.mediaUri.toString(),
//                        it.description.iconUri.toString()
//                    )
//                }
//                _mediaItems.postValue(Resource.success(items))
//            }
//        })
//    }
//
//    fun skipToNextSong() {
//        musicServiceConnection.transportControls.skipToNext()
//    }
//
//    fun skipToPreviousSong() {
//        musicServiceConnection.transportControls.skipToPrevious()
//    }
//
//    fun seekTo(pos: Long) {
//        musicServiceConnection.transportControls.seekTo(pos)
//    }
//
//    fun playOrToggleSong(mediaItem: Song, toggle: Boolean = false){
//        val isPrepared = playbackState.value?.isPrepared ?: false
//        if(isPrepared && mediaItem.mediaId == curPlayingSong?.value?.getString(METADATA_KEY_MEDIA_ID)){
//            playbackState.value?.let { playbackState ->
//                when {
//                    playbackState.isPlaying -> if(toggle) musicServiceConnection.transportControls.pause()
//                    playbackState.isPlayEnabled -> musicServiceConnection.transportControls.play()
//                    else -> Unit
//                }
//            }
//        } else {
//            musicServiceConnection.transportControls.playFromMediaId(mediaItem.mediaId, null)
//        }
//    }
//
//
//    override fun onCleared() {
//        super.onCleared()
//        musicServiceConnection.unsubscribe(MEDIA_ROOT_ID,object : MediaBrowserCompat.SubscriptionCallback() {
//
//
//        })
//    }
//}
//class MainViewModel @Inject constructor(
//    private val musicServiceConnection: MusicServiceConnection
//) : ViewModel() {
//    private val _mediaItems = MutableLiveData<Resource<List<Song>>>()
//    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems
//
//    val isConnected = musicServiceConnection.isConnected
//    val networkError = musicServiceConnection.networkError
//    val currentPlayingSong = musicServiceConnection.curPlayingSong
//    val playbackState = musicServiceConnection.playbackState
//
//    init {
//        _mediaItems.postValue(Resource.loading(null))
//        musicServiceConnection.subscribe(
//            MEDIA_ROOT_ID,
//            object : MediaBrowserCompat.SubscriptionCallback() {
//                override fun onChildrenLoaded(
//                    parentId: String,
//                    children: MutableList<MediaBrowserCompat.MediaItem>
//                ) {
//                    super.onChildrenLoaded(parentId, children)
//                    val items = children.map {
//                        Song(
//                            it.mediaId!!,
//                            it.description.title.toString(),
//                            it.description.subtitle.toString(),
//                            it.description.mediaUri.toString(),
//                            it.description.iconUri.toString()
//                        )
//                    }
//                    _mediaItems.postValue(Resource.success(items))
//                }
//            })
//    }
//
//    fun skipToNextSong() {
//        musicServiceConnection.transportControls.skipToNext()
//    }
//
//    fun skipToPreviousSong() {
//        musicServiceConnection.transportControls.skipToPrevious()
//    }
//
//    fun seekTo(position: Long) {
//        musicServiceConnection.transportControls.seekTo(position)
//    }
//
//    fun playOrToggleSong(mediaItem: Song, toggle: Boolean = false) {
//        val isPrepared = playbackState.value?.isPrepared ?: false
//        if(isPrepared && mediaItem.mediaId ==
//            currentPlayingSong?.value?.getString(METADATA_KEY_MEDIA_ID)) {
//            playbackState.value?.let { playbackState ->
//                when {
//                    playbackState.isPlaying -> if (toggle) musicServiceConnection.transportControls.pause()
//                    playbackState.isPlayEnabled -> musicServiceConnection.transportControls.play()
//                    else -> Unit
//                }
//            }
//        } else {
//            musicServiceConnection.transportControls.playFromMediaId(mediaItem.mediaId, null)
//        }
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        musicServiceConnection.unsubscribe(MEDIA_ROOT_ID, object :
//            MediaBrowserCompat.SubscriptionCallback(){})
//    }
//}
