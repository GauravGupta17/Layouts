package com.example.Repos

import android.util.Log
import com.example.models.PlaylistUsers
import com.example.models.SongInfo
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.koin.core.inject

class FirebaseRepo(private val db:FirebaseFirestore) : KoinComponent {


    fun addUser(user: PlaylistUsers) {
        db.collection("playlistUsers").document(user.email).set(user)
    }

    fun addSong(songInfo: SongInfo, userEmail: String) {

        db.collection("playlistUsers").document(userEmail).collection("songs")
            .document(songInfo.songName).set(songInfo)

    }

    fun getSongsList(userEmail: String){

        db.collection("playlistUsers").document(userEmail).collection("songs").get().addOnSuccessListener {
           Log.d( TAG,"${it.toObjects(SongInfo::class.java)}")

        }.addOnFailureListener {

        }
    }


    companion object{
        const val TAG ="FirebaseRepo"

    }


}