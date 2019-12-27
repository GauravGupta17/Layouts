package com.example.service

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ServiceTestRule
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeoutException


@RunWith(AndroidJUnit4::class)
class TestJobIntentServiceTest {
    private val serviceRule = ServiceTestRule()

    @Test
    @Throws(TimeoutException::class)
    fun testServiceTest() {
        val serviceIntent = Intent(
            ApplicationProvider.getApplicationContext<Context>(),
            TestJobIntentService::class.java
        )


    }
}