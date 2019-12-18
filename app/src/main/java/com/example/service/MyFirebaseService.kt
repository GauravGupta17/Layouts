package com.example.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        remoteMessage?.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: ${remoteMessage.data} ")

            remoteMessage.notification?.let {
              Log.d(TAG,"${it.body}")
            }


        }


    }

    companion object {
        const val TAG = "MyFireBaseService"
    }


}