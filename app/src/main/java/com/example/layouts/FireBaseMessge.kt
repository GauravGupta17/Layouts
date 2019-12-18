package com.example.layouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_firebase_messge.*

class FireBaseMessge : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_messge)



        FirebaseMessaging.getInstance().subscribeToTopic("Work").addOnCompleteListener {

            if (it.isSuccessful)
                Snackbar.make(firebase_messge_activity,"Subscribed to work",Snackbar.LENGTH_SHORT).show()

        }



        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {

            if (it.isSuccessful){

                val token =  it.result?.token
                val msg = getString(R.string.msg_token_fmt,token)
                Snackbar.make(firebase_messge_activity, msg, Snackbar.LENGTH_LONG).show()
            }

        }





    }
}