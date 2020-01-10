package com.example.layouts

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.API_BASE_URL
import com.example.adapters.GenAdapter
import com.example.clients.GitHubClient
import com.example.models.GitHubRepo
import kotlinx.android.synthetic.main.activity_retrofit.*
import kotlinx.android.synthetic.main.contact_view.view.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitActivity : AppCompatActivity() {
    lateinit var viewAdapter: GenAdapter<GitHubRepo, RepoViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        val logger = HttpLoggingInterceptor()

        logger.level = HttpLoggingInterceptor.Level.HEADERS

        val httpClient = OkHttpClient.Builder().addInterceptor(logger)

        val retrofit = Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).client(
                httpClient.build()
            ).build()

        val client = retrofit.create(GitHubClient::class.java)
        val call = client.reposForUser("GauravGupta17")

        call.enqueue(object : Callback<List<GitHubRepo>> {
            override fun onFailure(call: Call<List<GitHubRepo>>, t: Throwable) {

                Log.d(TAG, "Failed Respone : $t")

            }

            override fun onResponse(
                call: Call<List<GitHubRepo>>, response: Response<List<GitHubRepo>>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "${response.body()}")
                    infalteRv(response.body() as ArrayList<GitHubRepo>)

                }
            }
        })
    }

    fun infalteRv(list: ArrayList<GitHubRepo>) {

        viewAdapter =
            GenAdapter(list, { RepoViewHolder(it) }, R.layout.contact_view, { position, holder ->
                holder.itemView.tvName.text = list[position].name
                holder.itemView.tvFirstWord.text = "A"
            })

        rvRepoList.apply {
            layoutManager = LinearLayoutManager(this@RetrofitActivity)
            adapter = viewAdapter
        }


    }

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        const val TAG = "RetroFitActivity"
    }


}