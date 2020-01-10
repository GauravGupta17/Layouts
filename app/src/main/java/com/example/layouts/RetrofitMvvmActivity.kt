package com.example.layouts

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapters.GenAdapter
import com.example.models.GitHubRepo
import com.example.models.RetrofitVm
import kotlinx.android.synthetic.main.activity_retrofit_mvvm.*
import kotlinx.android.synthetic.main.song_view.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class RetrofitMvvmActivity : AppCompatActivity() {

    lateinit var viewAdapter: GenAdapter<GitHubRepo, RetrofitVH>
    private val retrofitVm by viewModel<RetrofitVm>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_mvvm)

        val list = arrayListOf<GitHubRepo>()

        retrofitVm.getRepoList().observe(this, Observer {
            list.clear()
            list.addAll(it)
            viewAdapter.notifyDataSetChanged()
        })

        viewAdapter = GenAdapter(list, { RetrofitVH(it) }, R.layout.song_view, { position, holder ->
            holder.itemView.tvSongName.text = list[position].name

        })
        rvRepoList.apply {
            layoutManager = LinearLayoutManager(this@RetrofitMvvmActivity)
            adapter = viewAdapter
        }

}

    class RetrofitVH(view: View) : RecyclerView.ViewHolder(view)

}