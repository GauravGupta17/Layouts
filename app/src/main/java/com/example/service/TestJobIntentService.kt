package com.example.service

import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val BROADCAST_ACTION = "com.example.layout.BROADCAST_ACTION"
const val EXTENDED_DATA_STATUS = "com.example.layout.STATUS"

class TestJobIntentService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        GlobalScope.launch(Dispatchers.IO) {

            for (i in 1..10) {
                delay(1000L)
                val localIntent = Intent(BROADCAST_ACTION).apply {
                    putExtra(EXTENDED_DATA_STATUS, i)
                }
                LocalBroadcastManager.getInstance(this@TestJobIntentService)
                    .sendBroadcast(localIntent)
                Log.d(TAG, "in test job intent service")
            }
        }

    }


    companion object {
        const val TAG = "TestJobIntentService"

    }


}