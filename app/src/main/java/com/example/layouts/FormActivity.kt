package com.example.layouts

import android.animation.LayoutTransition
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fragments.NameFragment
import com.example.fragments.PasswordFragment
import kotlinx.android.synthetic.main.activity_fragment_form.*
import kotlinx.android.synthetic.main.fragment_name.*
import kotlinx.android.synthetic.main.password_fragment.*

class FormActivity : AppCompatActivity() {

    var fragmentList = ArrayList<Fragment>()
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_form)
        formActivity.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        fragmentList = arrayListOf(
            PasswordFragment()
        )

        supportFragmentManager.beginTransaction().add(R.id.form_place_holder, NameFragment())
            .commit()


        fabNext.setOnClickListener {


            if (i >= fragmentList.size) {
                println("returned")
                return@setOnClickListener
            }

            supportFragmentManager.beginTransaction().apply {
                addToBackStack(null)
                replace(R.id.form_place_holder, fragmentList[i])
                commit()

            }
            i++
        }





    }

    override fun onBackPressed() {
        super.onBackPressed()
        i -= 1
    }


}