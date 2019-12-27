package com.example.layouts

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.adapters.GenAdapter
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_view_pager_2.*
import kotlinx.android.synthetic.main.view_intro_pager2.view.*
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

class ViewPager2Activity : AppCompatActivity() {

    lateinit var viewAdapter: GenAdapter<StorageReference, ViewPagerVH>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_2)
        pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val storageRef = FirebaseStorage.getInstance().reference
        val diRef = storageRef.child("images/")
        val tasks = diRef.listAll()
        var list = ArrayList<StorageReference>()
        pager2.offscreenPageLimit=2



          tasks.addOnSuccessListener {

              list =it.items as ArrayList<StorageReference>
              viewAdapter = GenAdapter(list, { ViewPagerVH(it) }, R.layout.view_intro_pager2,
                  { position, holder ->
                      val pathRef = storageRef.child("images/${list[position].name}")
                      pathRef.getBytes(ImageListActivity.TWO_MEGABYTES).addOnSuccessListener {

                         Glide.with(holder.itemView).load(it).transition(withCrossFade()).into(holder.itemView.ivViewPager)

                      }.addOnFailureListener {
                          Log.d(ImageListActivity.TAG,list[position].name)
                          Log.d(ImageListActivity.TAG, "Failed to Parse Image $it")

                      }
                  }

              )

              pager2.adapter = viewAdapter
          }

    }

    class ViewPagerVH(view: View) : RecyclerView.ViewHolder(view)


}