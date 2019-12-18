package com.example.layouts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapters.GenAdapter
import com.example.entities.StudentAndParent
import com.example.models.SchoolViewModel
import kotlinx.android.synthetic.main.activity_school_detail.*
import kotlinx.android.synthetic.main.student_detail_view.view.*

class SchoolDetailActivity : AppCompatActivity() {

    lateinit var viewAdapter: GenAdapter<StudentAndParent, SchoolViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_detail)

        val model = SchoolViewModel()

        val list = model.getAll()

        viewAdapter = GenAdapter(
            list,
            { SchoolViewHolder(it) },
            R.layout.student_detail_view
        ) { position, holder ->
            holder.itemView.tvId.text = list[position].student.id.toString()
            holder.itemView.tvStudentName.text = list[position].student.name
            Log.d(TAG,"anything")
            holder.itemView.tvFathersName.text = list[position].parent.fatherName
            holder.itemView.tvMothersName.text = list[position].parent.mothersName
            holder.itemView.tvClass.text = list[position].student.mClass.toString()

        }
        rvSchoolDetail.apply {
            layoutManager = LinearLayoutManager(this@SchoolDetailActivity)
            adapter = viewAdapter

        }


        fabSchoolSubmit.setOnClickListener {
            startActivity(Intent(this, SchoolInfoActivity::class.java))

        }


    }

    class SchoolViewHolder(view: View) : RecyclerView.ViewHolder(view)
    companion object{
        const val TAG = "SchoolDetailActivity"

    }


}
