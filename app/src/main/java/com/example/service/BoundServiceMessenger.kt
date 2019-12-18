package com.example.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.widget.Toast

class BoundServiceMessenger : Service(){
     private lateinit var messanger: Messenger

    internal class IncomingHandler(context: Context,private val applicationContext:Context = context.applicationContext) : Handler()
    {
        override fun handleMessage(msg: Message?) {

            Log.d(TAG,msg.toString())
            super.handleMessage(msg)
        }



    }
    override fun onBind(p0: Intent?): IBinder? {

        Toast.makeText(applicationContext,"binding",Toast.LENGTH_SHORT).show()
        messanger = Messenger(IncomingHandler(this))
        return messanger.binder
    }
companion object{
    const val TAG = "BoundServiceMessenger"
}


}