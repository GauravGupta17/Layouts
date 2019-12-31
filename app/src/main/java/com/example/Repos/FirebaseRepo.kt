package com.example.Repos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.FAILURE
import com.example.SUCCESS
import com.example.entities.Song
import com.example.models.PlaylistUsers
import com.example.models.PlaylistVm
import com.example.models.SongInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import org.koin.core.Koin
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseRepo(private val db: FirebaseFirestore) : KoinComponent {

    val firebaseAuth by inject<FirebaseAuth>()
    val email = firebaseAuth.currentUser!!.email ?: " "
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
        db.collection("playlistUsers").document(email).collection("songs")
            .document(songInfo.songName).set(songInfo).addOnFailureListener {
                flag = FAILURE
            }

        return flag
    }

   suspend fun getSongsList(): ArrayList<SongInfo> {

        list.ifEmpty {
            fetchData()
        }
        return list
    }

    private suspend fun fetchData() {

      db.collection("playlistUsers").document(email).collection("songs").get()
          .addOnSuccessListener {
               list = it.toObjects(SongInfo::class.java) as ArrayList<SongInfo>

           }.addOnFailureListener {

           }

    }

    fun listenToData() {


    }

    companion object {
        const val TAG = "FirebaseRepo"

    }


}