package com.example.layouts

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.Animaters.ZoomOutPageTransformer
import com.example.adapters.IntroScreenAdapter
import kotlinx.android.synthetic.main.activity_ui_controls.*

class ViewPagerActivity : AppCompatActivity() {

    lateinit var image: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_controls)
        val bg_color = resources.getIntArray(R.array.bg_array)
        pager.adapter = IntroScreenAdapter(supportFragmentManager)
        pager.setPageTransformer(true,ZoomOutPageTransformer())

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                Log.d(TAG, "$state")
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                pager.setBackgroundColor(bg_color[position])
            }

            override fun onPageSelected(position: Int) {

            }

        })


    }

    companion object {
        const val TAG = "ViewPagerActivity"
    }


}