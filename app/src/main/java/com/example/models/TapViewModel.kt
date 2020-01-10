package com.example.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TapViewModel : ViewModel() {

    val curScore = MutableLiveData<Int>()
    val timer = MutableLiveData<Int>()
    var nTimer: Int = 0
    var counter = 0
    val intialwords = arrayListOf<String>("Get", "Set", "Go")
    val words = MutableLiveData<String>()

    fun increment() {

        curScore.value = counter++

    }

    fun reset() {

        curScore.value = 0
        counter = 0
        nTimer = 0
        timer.value = nTimer
    }

    fun getCurScore(): Int {
        return counter
    }

    fun getTimer(): Int {

        return nTimer
    }

    fun startTimer() {
        viewModelScope.launch {

            for (s in intialwords) {
                delay(1000L)
                words.value = s
                Log.d("ViewModel", "${words.value}")
            }
            while (nTimer < 10) {
                delay(1000L)
                nTimer++
                timer.value = nTimer
                Log.d("ViewModel", "$nTimer")
            }

        }


    }


}