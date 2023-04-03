package com.example.myapplication.exoplayer.callback

import android.support.v4.media.MediaMetadataCompat
import com.example.myapplication.data.entities.Song
fun MediaMetadataCompat.toSong(): Song? {
    return description?.let {
        Song(
            it.mediaId?: "",
            it.title.toString(),
            it.subtitle.toString(),
            it.mediaUri.toString(),
            it.iconUri.toString()
        )

    }
}

//
//fun MediaMetadataCompat.toSong(): Song? {
//    return description?.let {
//        Song(
//            it.mediaId ?: "",
//            it.title.toString(),
//            it.subtitle.toString(),
//            it.mediaUri.toString(),
//            it.iconUri.toString()
//        )
//    }
//}
//class MusicServiceConnection(
//    context: Context
//) {
//    private val _isConnected = MutableLiveData<Event<Resource<Boolean>>>()
//    val isConnected: LiveData<Event<Resource<Boolean>>> = _isConnected
//
//    private val _networkError = MutableLiveData<Event<Resource<Boolean>>>()
//    val networkError: LiveData<Event<Resource<Boolean>>> = _networkError
//
//    private val _playbackState = MutableLiveData<PlaybackStateCompat?>()
//    val playbackState: LiveData<PlaybackStateCompat?> = _playbackState
//
//    private val _curPlayingSong = MutableLiveData<MediaMetadataCompat?>()
//    val curPlayingSong: LiveData<MediaMetadataCompat?> = _curPlayingSong
//
//    lateinit var mediaController: MediaControllerCompat
//
//    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)
//
//    private val mediaBrowser = MediaBrowserCompat(
//        context,
//        ComponentName(
//            context,
//            MusicService::class.java,
//
//            ),
//        mediaBrowserConnectionCallback,
//        null
//    ).apply { connect() }
//
//    val transportControls: MediaControllerCompat.TransportControls
//        get() = mediaController.transportControls
//
//    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
//        mediaBrowser.subscribe(parentId, callback)
//    }
//
//    fun unsubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
//        mediaBrowser.unsubscribe(parentId, callback)
//    }
//
//    private inner class MediaBrowserConnectionCallback(
//        private val context: Context
//    ) : MediaBrowserCompat.ConnectionCallback() {
//
//        override fun onConnected() {
//            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
//                registerCallback(MediaControllerCallback())
//            }
//            _isConnected.postValue(Event(Resource.success(true)))
//
//        }
//
//        override fun onConnectionSuspended() {
//            _isConnected.postValue(
//                Event(
//                    Resource.error(
//                        "bağlantı askıya alındı", false
//                    )
//                )
//            )
//
//        }
//
//        override fun onConnectionFailed() {
//            _isConnected.postValue(
//                Event(
//                    Resource.error(
//                        "medya tarayıcısına bağlanılamadı", false
//                    )
//                )
//            )
//
//        }
//
//    }
//
//    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {
//
//        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
//            _playbackState.postValue(state)
//        }
//
//        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
//            _curPlayingSong.postValue(metadata)
//        }
//
//        override fun onSessionEvent(event: String?, extras: Bundle?) {
//            super.onSessionEvent(event, extras)
//            when (event) {
//                NETWORK_ERROR -> _networkError.postValue(
//                    Event(
//                        Resource.error(
//                            "Sunucuya bağlanılamadı. lütfen internet bağlantınızı kontrol edin",
//                            null
//                        )
//                    )
//                )
//            }
//        }
//
//        override fun onSessionDestroyed() {
//            mediaBrowserConnectionCallback.onConnectionSuspended()
//        }
//    }
//}