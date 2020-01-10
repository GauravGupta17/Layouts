package com.example.models

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dao.UserDao
import com.example.database.UserDatabase
import com.example.entities.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : ViewModel() {
    var nameLive = MutableLiveData<String>()
    private var nameInsert = ""
    private var db: UserDao = UserDatabase.getDatabase(application).userDao()


    fun insert(name: String) {

        val user = Users(0, name, "dsdsds")


        viewModelScope.launch(Dispatchers.IO) {
            db.insertUserInfo(user)
        }
        nameInsert = name
    }

    fun updateTv() {
        nameLive.value = nameInsert

    }


}