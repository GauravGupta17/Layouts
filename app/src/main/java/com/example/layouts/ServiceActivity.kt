package com.example.layouts

import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Messenger
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.receivers.LiveReceivers
import com.example.service.*
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity() {

    private lateinit var mService: TestService
    private var nService: Messenger? = null
    private var mBound: Boolean = false
    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(className: ComponentName?) {
            mBound = false
        }

        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            val binder = service as TestService.TestBinder
            mService = binder.getService()
            mBound = true

        }

    }
    private val connectionMessanger = object : ServiceConnection {
        override fun onServiceDisconnected(className: ComponentName?) {
            mBound = false
        }

        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            nService = Messenger(service)
            mBound = true

        }

    }
    private val lr = LiveReceivers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)



        val statusFilters = IntentFilter(BROADCAST_ACTION)

        LocalBroadcastManager.getInstance(this).registerReceiver(lr, statusFilters)

        btnIntentService.setOnClickListener {
            Intent(this, TestIntentService::class.java).also {
                it.putExtra("data", "data")
                startService(it)
            }
        }

        btnService.setOnClickListener {
            Intent(this, TestJobIntentService::class.java).also {
                startService(it)
            }

        }

        btnOnBind.setOnClickListener {
            Intent(this, TestService::class.java).also {
                bindService(it, connection, Context.BIND_AUTO_CREATE)
            }
        }

        btnOnForeground.setOnClickListener {
            Intent(this, TestService::class.java).also {

                if (Build.VERSION.SDK_INT >= 26) {

                    startForegroundService(it)
                } else {
                    Log.d(TAG, "returned")
                    return@setOnClickListener
                }
            }

        }

        btnOnBindMessanger.setOnClickListener {
            Intent(this, BoundServiceMessenger::class.java).also {
                bindService(it, connectionMessanger, Context.BIND_AUTO_CREATE)
            }

        }



    }
/*
    override fun onPause() {
        super.onPause()
        mBound = false
        unregisterReceiver(lr)

    }*/

    companion object {
        const val TAG = "Service Activity"
    }


}