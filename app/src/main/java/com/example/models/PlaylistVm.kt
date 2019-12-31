package com.example.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Repos.FirebaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class PlaylistVm : ViewModel(), KoinComponent {
    val repo by inject<FirebaseRepo>()

    var songList = MutableLiveData<List<SongInfo>>()

    var list = ArrayList<SongInfo>()

    fun addUser(user: PlaylistUsers): Int {
        return repo.addUser(user)
    }

    fun addSong(songInfo: SongInfo): Int {
        return repo.addSong(songInfo)
    }

    fun getSongList(): ArrayList<SongInfo> {

        viewModelScope.launch(Dispatchers.IO) {
            list = repo.getSongsList()
        }

        songList.value = list
        return list
    }

    companion object {
        const val TAG = "PlaylistVm"
    }

}