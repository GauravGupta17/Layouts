package com.example.layouts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.entities.Song
import com.example.models.SongsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_song_form.*
import java.util.*

class SongFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_form)
        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("playlist")

        val model = ViewModelProviders.of(this)[SongsViewModel::class.java]
        val update = intent.hasExtra(PlayListActivity.SONGNAME)
        val songID = intent.getIntExtra(PlayListActivity.SONGID, 0)
        val position = intent.getIntExtra(PlayListActivity.POSITION, 0)


        if (update) {

            etSongName.setText(intent.getStringExtra(PlayListActivity.SONGNAME))
            etUrl.setText(intent.getStringExtra(PlayListActivity.SONGURL))
            btnAddSong.text = getString(R.string.Update)
            btnDelete.visibility = View.VISIBLE

        }


        btnAddSong.setOnClickListener {

            if (update) {
                model.updateSong(songID, etSongName.text.toString(), etUrl.text.toString())
                val intent = Intent()

                intent.apply {
                    putExtra(PlayListActivity.POSITION, position)
                    putExtra(PlayListActivity.SONGNAME, etSongName.text.toString())
                    putExtra(PlayListActivity.SONGURL, etUrl.text.toString())
                    putExtra(PlayListActivity.SONGID, songID)

                }

                setResult(UPDATE, intent)
            } else {

                model.insertSongs(etSongName.text.toString(), etUrl.text.toString())

                docRef.document(etSongName.text.toString())
                    .set(Song(0, etSongName.text.toString(), etUrl.text.toString()))
                    .addOnSuccessListener {
                        Log.d(TAG, "Added")

                    }.addOnFailureListener {
                    Log.d(TAG, "$it")
                }
                setResult(INSERT,
                    Intent().apply {
                        putExtra(PlayListActivity.SONGNAME, etSongName.text.toString())
                        putExtra(PlayListActivity.SONGURL, etUrl.text.toString())
                        putExtra(PlayListActivity.SONGID, songID)

                    }
                )

            }

            finish()
        }

        btnDelete.setOnClickListener {

            model.deleteSong(songID, etSongName.text.toString(), etUrl.text.toString())
            val intent = Intent()
            intent.putExtra(PlayListActivity.POSITION, position)
            setResult(DELETE, intent)
            finish()
        }


    }

    companion object {
        const val TAG = "SongFormActivity"
        const val UPDATE = 1
        const val DELETE = 2
        const val INSERT = 3

    }


}

