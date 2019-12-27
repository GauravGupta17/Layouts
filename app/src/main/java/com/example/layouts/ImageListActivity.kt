package com.example.layouts

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adapters.GenAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_image_list.*
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

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

                        Glide.with(holder.itemView).load(it).transition(withCrossFade())
                            .error(Glide.with(holder.itemView).load(R.drawable.man_image))
                            .into(holder.itemView.ivDowloadImageFireBase)

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

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        const val TAG = "ImageListActivity"
        const val TWO_MEGABYTES: Long = 2 * (1024 * 1024)
    }

}