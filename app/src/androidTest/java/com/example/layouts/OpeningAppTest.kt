package com.example.layouts


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

import androidx.test.uiautomator.UiDevice

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OpeningAppTest {
    lateinit var mDevice:UiDevice


    @Before
    fun openApp(){
        mDevice = UiDevice.getInstance(getInstrumentation())
        mDevice.pressHome()

    }

    @Test
    fun mainActivity_NotificationBar(){

        mDevice.openNotification()

    }






}