package com.example.layouts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.utilis.LifecycleListener
import kotlinx.android.synthetic.main.activity_files.*
import java.io.*
import java.lang.StringBuilder
import java.net.URI
import java.util.*

class FilesActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var image: Bitmap
    val albumName = "office"
    lateinit var file: File
    lateinit var filePath: String
    lateinit var photoUri: Uri
    var i =0

    /*private lateinit var test: LifecycleListener*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_files)

        /*lifecycle.addObserver(LifecycleListener {Toast.makeText(this,it,Toast.LENGTH_SHORT).show()})*/

        btnShow.isEnabled = false
        btnSave.isEnabled = false


        btnClick.setOnClickListener {
            createFile()
            takePicture()

            btnSave.isEnabled = true

        }

        btnSave.setOnClickListener {
            imgSave()
            btnShow.isEnabled = true

        }

        btnShow.setOnClickListener {
            imgShow()
        }


    }

    private fun imgShow() {

       FileInputStream(file).use {
           val image = BitmapFactory.decodeStream(it)
           ivSaveImage.setImageBitmap(image)

       }

    }

    private fun imgSave() {
        FileOutputStream(file,true).use {

            val stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val bitmapByteArray = stream.toByteArray()
            it.write(bitmapByteArray)

        }

    }

    private fun createFile() {

        val path = File(Environment.getExternalStoragePublicDirectory("Gaurav"),albumName).apply {  mkdirs()}
        i++

        file = File(path,"image1$i.png").apply {
            createNewFile()

        }


            if (!path.exists())
                println("not exist")
        }




    private fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { picIntent ->
            picIntent.resolveActivity(packageManager)?.also {

                startActivityForResult(picIntent, REQUEST_IMAGE_CAPTURE)

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            image = data?.extras?.get("data") as Bitmap
        }

    }


}