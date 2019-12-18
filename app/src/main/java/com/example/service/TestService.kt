package com.example.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestService : Service() {
    var value =0

    private val binder = TestBinder()

    override fun onBind(intent: Intent?): IBinder? {


        GlobalScope.launch {
            for (i in 1..7) {
                delay(1000L)
                println("$i on Bind")
            }
        }

        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        GlobalScope.launch {
            for (i in 1..7) {
                value =i
                delay(1000L)
                println(i)
            }
        }

        return START_STICKY
    }

    fun getvalue():Int = value

    inner class TestBinder : Binder() {

        fun getService(): TestService = this@TestService
    }


}