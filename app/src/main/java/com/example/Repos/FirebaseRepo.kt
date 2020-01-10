package com.example.Repos


import com.example.FAILURE
import com.example.SUCCESS
import com.example.models.PlaylistUsers
import com.example.models.SongInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.koin.core.inject


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

    fun getSongsList(): ArrayList<SongInfo> {

        list.ifEmpty {
            fetchData()
        }
        return list
    }

    private fun fetchData() {

      db.collection("playlistUsers").document(email).collection("songs").get()
          .addOnSuccessListener {
               list = it.toObjects(SongInfo::class.java) as ArrayList<SongInfo>

           }.addOnFailureListener {

           }

    }
  companion object {
        const val TAG = "FirebaseRepo"

    }


}