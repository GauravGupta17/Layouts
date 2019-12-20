package com.example.worker

import android.app.Notification
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.models.BlurViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class SaveImageFileWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private val Title = "Blurred Image"
    private val dataFormatter = SimpleDateFormat(
        "yyyy.MM.dd 'at' HH:mm:ss z",
        Locale.getDefault()
    )
    override val coroutineContext = Dispatchers.IO

    override suspend fun doWork(): Result {

        val resolver = applicationContext.contentResolver

        return try {
            setForeground(createForegroundInfo())
            delay(3000L)
            Log.d(TAG, "Inside saveImageFileWorker")
            val resourceUri = inputData.getString(BlurViewModel.KEY_IMAGE_URI)
            val bitmap = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )

            val imageUri = MediaStore.Images.Media.insertImage(
                resolver, bitmap, Title, dataFormatter.format(
                    Date()
                )
            )
            if (!imageUri.isNullOrEmpty()) {
                Log.d(TAG, "Inside saveImageFileWorker")
                return Result.success(workDataOf(BlurViewModel.KEY_IMAGE_URI to imageUri))
            } else {
                Log.e(TAG, "Writing to mediaStore Failed")
                Result.failure()
            }
        } catch (e: Exception) {
            Log.e(TAG, "$e")
            Result.failure()
        }


    }


    private fun createForegroundInfo(): ForegroundInfo{

        val notification =
            NotificationCompat.Builder(applicationContext, "notiSave").setContentTitle("Workmanger")
                .setContentText("Saving the image in gallery").build()

        return ForegroundInfo(notification)
    }

    companion object {
        const val TAG = "SaveImageFileWorker"

    }

}