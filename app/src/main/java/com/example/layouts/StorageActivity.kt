package com.example.layouts

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_storage.*
import java.io.ByteArrayOutputStream
import java.util.*

class StorageActivity : AppCompatActivity() {
    private var selectedImageUri: Uri? = null
    lateinit var bitmap: Bitmap
    private val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        btnGetImage.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_GET_CONTENT
                type = "images/jpeg"
            }

            startActivityForResult(intent, GET_IMAGE)

        }
        btnUpload.setOnClickListener {


            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 20, stream)

            val bitmapByteArray = stream.toByteArray()
            val path = "images/" + UUID.randomUUID() + ".png"

            val ref = storage.reference.child(path)
            val uploadTask = ref.putBytes(bitmapByteArray)

            uploadTask.addOnFailureListener {
                Snackbar.make(storage_activity, "Upload Failed", Snackbar.LENGTH_LONG).show()

            }.addOnCompleteListener {

                Snackbar.make(storage_activity, "Upload Complete", Snackbar.LENGTH_SHORT).show()

                if (it.isComplete) {
                    beforeUpload()

                }
            }.addOnProgressListener {
                pbUpload.visibility = View.VISIBLE
                btnUpload.visibility = View.GONE
            }

        }
        btnImageList.setOnClickListener {
            val intent = Intent(this, ImageListActivity::class.java)
            intent.putExtra("ref", storage.reference.toString())
            startActivity(intent)

        }

        btnClickImage.setOnClickListener {
            takePicture()
        }


    }

    private fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { picIntent ->
            picIntent.resolveActivity(packageManager)?.also {

                startActivityForResult(picIntent, REQUEST_IMAGE_CAPTURE)

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        storage.reference.let {
            outState.putString("ref", it.toString())
            outState.putString("uri", selectedImageUri.toString())
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        val stringRef = savedInstanceState?.getString("ref") ?: return
        val imageUri = savedInstanceState?.getString("uri")
        ivLoadImage.setImageURI(Uri.parse(imageUri))
        ivLoadImage.visibility = View.VISIBLE
        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(stringRef)
        val tasks = storageRef.activeUploadTasks
        if (tasks.size > 0) {
            val task = tasks[0]

            task.addOnProgressListener {
                pbUpload.visibility = View.VISIBLE
                btnGetImage.visibility = View.GONE
            }

            task.addOnCompleteListener {
                if (it.isComplete) {
                    Snackbar.make(
                        storage_activity,
                        "restore instance uploaced",
                        Snackbar.LENGTH_LONG
                    ).show()
                    pbUpload.visibility = View.GONE
                    ivLoadImage.visibility = View.GONE
                    btnGetImage.visibility = View.VISIBLE

                }
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                selectedImageUri = data?.data
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                afterLoadImage()
            }

        }
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                bitmap = data?.extras?.get("data") as Bitmap
                ivLoadImage.setImageBitmap(bitmap)
                afterLoadImage()


            }
        }


    }

    private fun afterLoadImage() {
        ivLoadImage.visibility = View.VISIBLE
        ivLoadImage.setImageBitmap(bitmap)
        btnGetImage.visibility = View.GONE
        btnUpload.visibility = View.VISIBLE
        btnClickImage.visibility = View.GONE
        tvOr.visibility = View.GONE
    }

    private fun beforeUpload() {
        btnUpload.visibility = View.GONE
        pbUpload.visibility = View.GONE
        btnGetImage.visibility = View.VISIBLE
        btnClickImage.visibility = View.VISIBLE
        ivLoadImage.visibility = View.GONE
        tvOr.visibility = View.VISIBLE

    }

    companion object {
        const val GET_IMAGE = 1
        const val REQUEST_IMAGE_CAPTURE = 2

    }


}