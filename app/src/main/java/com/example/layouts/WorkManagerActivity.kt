package com.example.layouts

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.WorkManager
import com.example.fragments.ClickFragment
import com.example.fragments.ImageFilterFragment
import com.example.models.BlurViewModel
import com.example.utilis.UtilsFragments
import kotlinx.android.synthetic.main.fragment_image_filter.*
import java.util.ArrayList

class WorkManagerActivity : AppCompatActivity(), UtilsFragments {

    lateinit var model: BlurViewModel
    private var mPermissionCount: Int = 0
    private var undoList = ArrayList<Uri>()
    private var saveImageUri = ""


    override fun undo() {
        val image = BitmapFactory.decodeStream(
            this.contentResolver.openInputStream(undoList.first())
        )
        ivClickedImage.setImageBitmap(image)
        btnUndo?.isEnabled = false
        tvMessage.text = getString(R.string.Blurlvlmessage)
    }

    override fun getImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GET_IMAGE)
    }

    override fun blurImage() {
        model.applyBlur(getBlurLevel())
        progress_bar?.visibility = View.VISIBLE
        tvMessage.text = getString(R.string.savingMessage)
        btnUndo.isEnabled = true

    }

    override fun clickImage() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { picIntent ->
            picIntent.resolveActivity(packageManager)?.also {

                startActivityForResult(picIntent, CLICK_IMAGE_REQUEST)

            }
        }

    }

    override fun changeFragments() {
        supportFragmentManager.beginTransaction().remove(ClickFragment()).commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.worker_place_holder, ImageFilterFragment()).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)

        requestPermissionNecessary()

        WorkManager.getInstance(applicationContext)
            .getWorkInfosByTagLiveData(BlurViewModel.BLUR_WORKER).observe(
                this, Observer {
                    if (it.size > 0) {
                        val uri: String? = it[0].outputData.getString(BlurViewModel.KEY_IMAGE_URI)
                        uri?.let {
                            saveImageUri = it
                            Log.d(TAG, "changing picture")
                            ivClickedImage?.setImageURI(Uri.parse(it))
                            progress_bar?.visibility = View.GONE
                            btnUndo?.isEnabled = true
                        }
                    }

                }
            )
        model = ViewModelProviders.of(this)[BlurViewModel::class.java]

        supportFragmentManager.beginTransaction().replace(R.id.worker_place_holder, ClickFragment())
            .commit()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CLICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val image = data?.extras?.get("data") as Bitmap
            ivClickedImage.setImageBitmap(image)
        }
        if (requestCode == GET_IMAGE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            model.setImageUri(selectedImageUri)

            selectedImageUri?.let {
                undoList.add(it)
                val image = BitmapFactory.decodeStream(
                    this.contentResolver.openInputStream(selectedImageUri)
                )
                btnUndo?.isEnabled = false
                ivClickedImage.setImageBitmap(image)
            }

        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_PERMISSIONS_REQUEST_COUNT, mPermissionCount)
    }

    override fun onBackPressed() {
        model.saveImage(saveImageUri)
        super.onBackPressed()
    }

    private fun requestPermissionNecessary() {
        if (!checkPermission()) {
            if (mPermissionCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                mPermissionCount += 1
                ActivityCompat.requestPermissions(this, sPermissions, REQUEST_CODE_PERMISSIONS)

            } else {
                Toast.makeText(this, "set persmissions in  String", Toast.LENGTH_SHORT).show()

            }


        }


    }

    private fun checkPermission(): Boolean {
        var hasPermission = true

        for (permission in sPermissions) {
            hasPermission = ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED

        }

        return hasPermission
    }

    private fun getBlurLevel(): Int {

        rgOptions?.let {
            return when (it.checkedRadioButtonId) {
                rbBLittleBlurred.id -> 1
                rbMoreBlurred.id -> 2
                rbMostBlurred.id -> 3
                else -> 1
            }
        } ?: return 1


    }

    companion object {
        const val TAG = "WorkManagerActivity"
        const val CLICK_IMAGE_REQUEST = 1
        const val GET_IMAGE = 2
        private const val REQUEST_CODE_PERMISSIONS = 101
        private const val KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT"
        private const val MAX_NUMBER_REQUEST_PERMISSIONS = 2
        private val sPermissions: Array<String> = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }


}