package com.example.service

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class TestIntentService : IntentService("TestIntentService") {
   private var dataString: String? = null

    override fun onHandleIntent(workIntent: Intent) {
        dataString = workIntent.getStringExtra("data")

        LocalBroadcastManager.getInstance(this@TestIntentService).sendBroadcast(Intent(Intent.ACTION_SEND).apply {
            putExtra(VALUE_STATUS,23)
        })


        }



companion object{
    const val TAG ="TestIntentService"
    const val ACTION = "action"
    const val VALUE_STATUS ="value"
}

}