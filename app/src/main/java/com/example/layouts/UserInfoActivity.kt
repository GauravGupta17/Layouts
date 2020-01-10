package com.example.layouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.models.UserViewModel
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity() {

    private val observable = Observer<String> {
        tvDatabase.text = it
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val model = UserViewModel(application)

        model.nameLive.observe(this, observable)

        btnSubmit.setOnClickListener {
            model.insert(etDbName.text.toString())
            model.updateTv()
        }


    }


}