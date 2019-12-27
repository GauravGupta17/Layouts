package com.example.models

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.worker.BlurWorker
import com.example.worker.CleanWorker
import com.example.worker.SaveImageFileWorker
import org.koin.core.KoinComponent
import org.koin.core.inject

class BlurViewModel : ViewModel(),KoinComponent {

    private val workManager by inject<WorkManager>()
    var uri: Uri? = null
    var blurlevel:Int =0
    lateinit var activitContext:Context


    fun applyBlur(blurLevel: Int) {

        this.blurlevel =blurLevel
        val cleanTask = OneTimeWorkRequest.from(CleanWorker::class.java)

        val blurTask = OneTimeWorkRequestBuilder<BlurWorker>().addTag(BLUR_WORKER).setInputData(getInputData()).build()

        workManager.beginUniqueWork(BLUR_IMAGE_WORK, ExistingWorkPolicy.KEEP, cleanTask)
            .then(blurTask).enqueue()

    }

    fun setImageUri(uri: Uri?) {
        this.uri = uri
    }

    fun saveImage(saveImageUri:String){
        val saveData = workDataOf(KEY_IMAGE_URI to saveImageUri)

        /*val constraints = Constraints.Builder().setRequiresCharging(true).build()*/
        val saveTask = OneTimeWorkRequestBuilder<SaveImageFileWorker>().setInputData(saveData).addTag(SAVE_WORKER).build()
        workManager.enqueue(saveTask)
    }


    private fun getStringImageUri(): String {
        return uri.toString()
    }

    private fun getInputData(): Data {

        return workDataOf(KEY_IMAGE_URI to getStringImageUri(), BLUR_LEVEL to blurlevel )
    }



    companion object {
        const val KEY_IMAGE_URI = "key image uri"
        const val BLUR_IMAGE_WORK = "blur image work"
        const val BLUR_LEVEL = "blur level"
        const val SAVE_WORKER = "saveImageWorker"
        const val BLUR_WORKER ="blurworker"
    }


}