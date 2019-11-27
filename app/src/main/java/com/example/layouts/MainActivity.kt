package com.example.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapters.ActivityAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val activitylist = arrayListOf<String>(
            "Motion Layout",
            "Image Filter Motion Layout",
            "Recycler View "

        )




        viewManager = LinearLayoutManager(this)
        viewAdapter = ActivityAdapter(activitylist)


        mainRV.apply {

            layoutManager = viewManager
            adapter = viewAdapter

        }


    }
}
