package com.example.Repos

import com.example.models.PlaylistUsers
import com.example.models.SongInfo
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.koin.core.inject

class FirebaseRepo :KoinComponent  {

    val db by inject<FirebaseFirestore>()

    fun addUser(user:PlaylistUsers){
     db.collection("playlistUsers").document(user.email).set(user)
    }

    fun getList(email:String):List<SongInfo>{

        return emptyList()
    }
    fun addSong(){

    }




}