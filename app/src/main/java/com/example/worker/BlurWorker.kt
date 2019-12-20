package com.example.worker

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.models.BlurViewModel
import com.example.utilis.WorkerUtils
import kotlinx.coroutines.Dispatchers

class BlurWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override val coroutineContext = Dispatchers.IO
    override suspend fun doWork(): Result {
        val appContext = applicationContext

        setForeground(createForegroundInfo())
        return try {

            val imageUri = inputData.getString(BlurViewModel.KEY_IMAGE_URI)
            val blurLevel = inputData.getInt(BlurViewModel.BLUR_LEVEL,1)

            val bitmap = BitmapFactory.decodeStream(
                appContext.contentResolver.openInputStream(
                    Uri.parse(imageUri)
                )
            )
            val output = WorkerUtils.blurBitmap(bitmap, appContext,blurLevel)

            val outputUri = WorkerUtils.writeBitmapToFile(appContext, output)

            val outPutData = workDataOf(BlurViewModel.KEY_IMAGE_URI to outputUri.toString())

            Result.success(outPutData)

        } catch (throwable: Throwable) {
            Log.d(TAG, "$throwable")
            Result.failure()
        }
    }

   private fun createForegroundInfo(): ForegroundInfo{

        val notification =
            NotificationCompat.Builder(applicationContext, "Noti").setContentTitle("Workmanger")
                .setContentText("Bluring the image").build()

        return ForegroundInfo(notification)
    }

    companion object {
        const val TAG = "BlurWorker"
    }

}