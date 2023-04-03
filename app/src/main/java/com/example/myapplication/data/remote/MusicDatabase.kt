package com.example.myapplication.data.remote

import android.util.Log
import com.example.myapplication.data.entities.Song
import com.example.myapplication.other.Constants.SONG_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
private const val TAG = "MusicDatabase"
class MusicDatabase {

    private val firestore = FirebaseFirestore.getInstance()
    private val songCollection = firestore.collection(SONG_COLLECTION)
//suspend
suspend fun getAllSongs(): List<Song> {
        return try {
            songCollection.get().await().toObjects(Song::class.java)

        } catch (e: Exception) {
            Log.e(TAG,"Could not retrieve song list")
            e.printStackTrace()
            emptyList()
        }
    }
}

