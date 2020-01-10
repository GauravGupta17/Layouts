package com.example

import android.app.Application
import androidx.work.Configuration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

lateinit var appctx:Application

class App :Application(),Configuration.Provider {

    override fun getWorkManagerConfiguration():Configuration = Configuration.Builder().build()

    init {
        appctx = this
    }


    override fun onCreate() {
        super.onCreate()
        appctx = this

        startKoin {
            androidContext(this@App)
            modules(listOf(vmModule, appModule, firebaseModule, retrofitModule))
        }
    }





}