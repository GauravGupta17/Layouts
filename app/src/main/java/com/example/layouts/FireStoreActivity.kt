package com.example.layouts

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.models.PlaylistUsers
import com.example.models.SongInfo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_firestore.*

class FireStoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestore)
        val db = FirebaseFirestore.getInstance()

        val list = listOf<SongInfo>()

        db.collection("playlist").get().addOnSuccessListener {

            it?.let {
                val size = it.documents.size
                Log.d(TAG, "${it.documents[0].data?.get("songName")}")

                for (i in 0 until size)
                    tvFsData.text = it.documents[i].data?.get("songName") as String
            }

        }


    }

    companion object {
        const val TAG = "FireStoreActivity"
    }
}


