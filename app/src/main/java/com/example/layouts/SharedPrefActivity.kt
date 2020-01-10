package com.example.layouts

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.models.TapViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_shared_pref.*

class SharedPrefActivity : AppCompatActivity() {

    private var highScore = 0
    var sharedPref: SharedPreferences? = null
    private val changeObserveScore = Observer<Int> {
        tvTap.text = it.toString()
    }

    private val changeObserverTimer = Observer<Int> {
        tvSec.text = it.toString()

    }
    private val changeObserverWords = Observer<String> {
        tvSec.text = it
    }

    val model: TapViewModel by lazy {
        ViewModelProviders.of(this)[TapViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_pref)


        model.startTimer()


        sharedPref = getSharedPreferences(getString(R.string.sharedPrefKey), Context.MODE_PRIVATE)
        tvHighScoreValue.text = sharedPref?.getString(getString(R.string.sharedPrefKey), "-1")
        highScore = tvHighScoreValue.text.toString().toInt()

        model.curScore.observe(this, changeObserveScore)
        model.timer.observe(this, changeObserverTimer)
        model.words.observe(this, changeObserverWords)



        tvTap.setOnClickListener {

            if (model.nTimer == 0) {
                return@setOnClickListener
            }


            if (model.nTimer == 10) {

                setHighScoreG(model.getCurScore())
                tvHighScoreValue.text = sharedPref?.getString(getString(R.string.sharedPrefKey), "-1")

                Snackbar.make(it, "Game Over", Snackbar.LENGTH_INDEFINITE).setAction("Try Again") {
                    model.reset()
                    reset()
                    model.startTimer()

                }.show()


                return@setOnClickListener
            }
            model.increment()

        }


    }

    private fun reset() {
        tvTap.text = "0"
        tvSec.text = " "
    }

    private fun setHighScoreG(curScore: Int) {

        if (highScore >= curScore) {
            return
        } else {
            highScore = curScore
            sharedPref?.edit().let {

                it?.putString(getString(R.string.sharedPrefKey), highScore.toString())
                it?.apply()
            }


        }

    }


}

