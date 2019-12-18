package com.example.layouts

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapters.GenAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_image_list.*
import kotlinx.android.synthetic.main.activity_storage.*
import kotlinx.android.synthetic.main.activity_storage.view.*
import kotlinx.android.synthetic.main.fire_base_image_view.view.*

class ImageListActivity : AppCompatActivity() {

    lateinit var viewAdapter: GenAdapter<StorageReference, ImageViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)

        val stringRef = intent.getStringExtra("ref")
        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(stringRef)
        val diRef = storageRef.child("images/")
        val tasks = diRef.listAll()
        var list = ArrayList<StorageReference>()

        tasks.addOnSuccessListener { it ->
            list = it.items as ArrayList<StorageReference>
            Log.d(TAG, "$list")

            viewAdapter = GenAdapter(list, { ImageViewHolder(it) }, R.layout.fire_base_image_view,
                { position, holder ->
                    val pathRef = storageRef.child("images/${list[position].name}")
                    Log.d(TAG, "$pathRef")

                    pathRef.getBytes(TWO_MEGABYTES).addOnSuccessListener {
                        val image = decodeBitmap(it)

                        holder.itemView.ivDowloadImageFireBase.setImageBitmap(image)

                    }.addOnFailureListener {

                        Log.d(TAG, "Failed to Parse Image $it")
                    }
                }

            )

            rvImageList.apply {

                layoutManager = GridLayoutManager(this@ImageListActivity, 3)
                adapter = viewAdapter

            }

        }.addOnFailureListener {
            Snackbar.make(Image_List_Activity, "Failed to Parse List", Snackbar.LENGTH_LONG).show()
        }


    }

    private fun decodeBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, this)
            inSampleSize = calulateSampleSize(this, 100, 100)
            inJustDecodeBounds = false
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, this)

        }

    }

    private fun calulateSampleSize(
        options: BitmapFactory.Options,
        reqHeight: Int,
        reqWidth: Int
    ): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfWidth = width / 2
            val halfHeight = height / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        const val TAG = "ImageListActivity"
        const val TWO_MEGABYTES: Long = 2 * (1024 * 1024)
    }

}