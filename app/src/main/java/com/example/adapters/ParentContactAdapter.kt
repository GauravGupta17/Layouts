package com.example.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.layouts.R
import com.example.models.ContactInfo
import com.example.utilis.giveStatus
import com.example.utilis.inflate

import kotlinx.android.synthetic.main.activity_parent_rv.view.*

class ParentContactAdapter(private val contactList: MutableMap<Int,ArrayList<ContactInfo>>) :
    RecyclerView.Adapter<ParentContactAdapter.ViewHolder>() {

    private val recyclePool = RecyclerView.RecycledViewPool()




    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val recyclerView: RecyclerView = itemView.rvChild

        fun setTextView(post: String) {
            itemView.tvParentRv.text = post

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {



        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_parent_rv, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.setTextView(position.giveStatus())

            val childLayout = LinearLayoutManager(holder.recyclerView.context)

            holder.recyclerView.apply {

                layoutManager = childLayout
                adapter = contactList[position]?.let { ContactAdapter(it) }
                setRecycledViewPool(recyclePool)


            }
        }








    override fun getItemCount() :Int {


        Log.d("ParentContactAdapter","${contactList.count()}")

        return contactList.count()


    }

}
