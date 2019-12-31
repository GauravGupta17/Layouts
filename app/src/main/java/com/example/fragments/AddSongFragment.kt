package com.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.FAILURE
import com.example.layouts.R
import com.example.models.LogInVm
import com.example.models.PlaylistVm
import com.example.models.SongInfo
import kotlinx.android.synthetic.main.fragment_add_song.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class AddSongFragment :Fragment(){

    private val playlistVm by sharedViewModel<PlaylistVm>()
    private val logInVm by sharedViewModel<LogInVm> ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_song,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSubmit.setOnClickListener {
            validateAndAdd(etSongName.text.toString(),etSongUrl.text.toString())
        }

        logInVm.authState.observe(this, androidx.lifecycle.Observer {
            when (it) {

                LogInVm.AuthenticationState.SIGNOUT -> findNavController().navigate(R.id.action_addSongFragment_to_loginFragment)

                else -> println("signout failed")
            }
        })


    }

    private fun validateAndAdd(name:String,url:String){
        if (name.isBlank() && url.isBlank()) {
            Toast.makeText(context, "Enter Credentials", Toast.LENGTH_LONG).show()
            return
        }
        else{
            addSong(name, url)
        }
    }

    private fun addSong(name: String, url: String) {

       if (playlistVm.addSong(SongInfo(name,url)) == FAILURE){
           Toast.makeText(context,"Adding Song Failed",Toast.LENGTH_LONG).show()
           return
       }



    }
}