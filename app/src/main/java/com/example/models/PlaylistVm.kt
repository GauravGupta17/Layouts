package com.example.models

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.FAILURE
import com.example.SUCCESS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PlaylistVm(private val db: FirebaseFirestore, private val auth: FirebaseAuth) : ViewModel() {

    var songList = MutableLiveData<ArrayList<SongInfo>>()
    var list = ArrayList<SongInfo>()

    fun addUser(user: PlaylistUsers): Int {
        var flag = SUCCESS
        db.collection("playlistUsers").document(user.email).set(user).addOnFailureListener {
            flag = FAILURE
        }
        return flag
    }

    fun addSong(songInfo: SongInfo): Int {
        var flag = SUCCESS
        val email = auth.currentUser?.email ?: " "
        db.collection("playlistUsers").document(email).collection("songs")
            .document(songInfo.songName).set(songInfo).addOnFailureListener {
                flag = FAILURE
            }
        return flag
    }

    fun getSongInfoList(): MutableLiveData<ArrayList<SongInfo>> {
        val email = auth.currentUser?.email ?: " "
        db.collection("playlistUsers").document(email).collection("songs").get()
            .addOnSuccessListener {
                songList.value = it.toObjects(SongInfo::class.java) as ArrayList<SongInfo>

            }.addOnFailureListener {

            }
        return songList
    }

    companion object {
        const val TAG = "PlaylistVm"
    }

}