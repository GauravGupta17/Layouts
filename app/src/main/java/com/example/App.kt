package com.example

import android.app.Application
import androidx.work.Configuration

lateinit var appctx:Application

class App :Application(),Configuration.Provider {

    override fun getWorkManagerConfiguration():Configuration = Configuration.Builder().build()

    init {
        appctx = this
    }


    override fun onCreate() {
        super.onCreate()
        appctx = this

    }





}