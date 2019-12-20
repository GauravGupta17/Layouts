package com.example.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.layouts.R
import com.example.layouts.WorkManagerActivity
import com.example.utilis.UtilsFragments
import kotlinx.android.synthetic.main.fragment_image_filter.*

class ImageFilterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_image_filter,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d(TAG, "onActivityCreated")
        val cf = activity as UtilsFragments

       btnGo.setOnClickListener {
           cf.blurImage()
       }

       btnUndo.setOnClickListener {
           Log.d(TAG,"undo")
           cf.undo()
       }


    }


    companion object{
        const val TAG ="ImageFilterFragment"
        const val CLICK_IMAGE_REQUEST = 1
        const val GET_IMAGE = 2
    }



}