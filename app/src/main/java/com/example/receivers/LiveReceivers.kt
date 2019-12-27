package com.example.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.service.BROADCAST_ACTION
import com.example.service.EXTENDED_DATA_STATUS
import android.content.Context.NOTIFICATION_SERVICE
import com.example.layouts.R


class LiveReceivers : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {


        if (intent.action == BROADCAST_ACTION) {

            Log.d(TAG, "in BroadCastReceiver  ")

            val notication =
                NotificationCompat.Builder(context, "1").setContentTitle("Android Training App")
                    .setSmallIcon(
                        R.drawable.com_facebook_button_send_icon_white
                    ).setContentText("${intent.extras?.get(EXTENDED_DATA_STATUS)}")
                    .setPriority(NotificationCompat.PRIORITY_HIGH).build()

            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notication)

        }

    }

    companion object {
        const val TAG = "LiveRecievers"
    }


}