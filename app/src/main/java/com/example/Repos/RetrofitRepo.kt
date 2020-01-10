package com.example.Repos

import com.example.clients.GitHubClient
import com.example.models.GitHubRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RetrofitRepo ( val retrofit: Retrofit){

    val client = retrofit.create(GitHubClient::class.java)
    val call = client.reposForUser("GauravGupta17")

   fun getRepoList() : List<GitHubRepo>{

        val list =  arrayListOf<GitHubRepo>()

           call.enqueue(object : Callback<List<GitHubRepo>> {
               override fun onFailure(call: Call<List<GitHubRepo>>, t: Throwable) {
                   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
               }

               override fun onResponse(
                   call: Call<List<GitHubRepo>>,
                   response: Response<List<GitHubRepo>>
               ) {

                   if (response.isSuccessful) {
                       response.body()?.let {
                           list.addAll(it)
                           return

                       }
                   }
               }

           })

    return list
    }







}