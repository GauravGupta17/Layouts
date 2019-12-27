package com.example.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.layouts.R
import com.example.models.BlurViewModel
import com.example.utilis.UtilsFragments
import kotlinx.android.synthetic.main.fragment_click.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ClickFragment : Fragment() {

    private val model by sharedViewModel<BlurViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_click, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
        val cf = activity as UtilsFragments
        btnClick.setOnClickListener {
            cf.clickImage()
            cf.changeFragments()
        }

        btnGetImage.setOnClickListener {

            cf.getImage()
            cf.changeFragments()
        }

    }
    companion object{
        const val TAG ="ClickFragment"
    }



}