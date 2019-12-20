package com.example.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.lang.Exception

class CleanWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {


     override val coroutineContext = Dispatchers.IO
    override suspend fun doWork(): Result {
        val outputDirectory = File(applicationContext.filesDir, OUTPUT_PATH)
        try {
            if (outputDirectory.exists()) {
                val enteries = outputDirectory.listFiles()
                if (enteries != null) {
                    for (entry in enteries) {
                        val name = entry.name
                        if (name.isNotEmpty() && name.endsWith(".png")) {
                            val deleted = entry.delete()
                            Log.d(TAG, "$deleted")
                        }
                    }
                }
            }

            return Result.success()
        } catch (e: Exception) {

            return Result.failure()
        }
    }

    companion object {
        const val TAG = "CleanWorker"
        const val OUTPUT_PATH = "blur_filter_outputs";
    }

}