package com.example.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clients.GitHubClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RetrofitVm(val retrofit : Retrofit) :ViewModel(){

    var LiveList = MutableLiveData<List<GitHubRepo>>()
    var list = listOf<GitHubRepo>()
    val client = retrofit.create(GitHubClient::class.java)
    val call = client.reposForUser("GauravGupta17")


    fun getRepoList() : LiveData<List<GitHubRepo>> {
      call.enqueue(object:Callback<List<GitHubRepo>>{
         override fun onFailure(call: Call<List<GitHubRepo>>, t: Throwable) {
           Log.d(TAG,"Failed Fethcing")
         }

         override fun onResponse(
             call: Call<List<GitHubRepo>>,
             response: Response<List<GitHubRepo>>
         ) {
             if (response.isSuccessful)
             {
                 LiveList.value = response.body()
             }
         }
     })
     return LiveList
 }

    companion object{
        const val TAG ="RetroFitVM"
    }
}