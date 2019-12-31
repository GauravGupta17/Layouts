package com.example.layouts

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.REQUEST_CODE_PERMISSIONS
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_dynamic_permission.*

class DynamicPermissionActivity : AppCompatActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_permission)

        btnTakePermission.setOnClickListener {
            showCamera()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == 1) {

            if (grantResults.size == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Snackbar.make(dynamic_per_layout, "Permission granted", Snackbar.LENGTH_LONG).show()

            } else {
                Snackbar.make(dynamic_per_layout, "permission denied", Snackbar.LENGTH_LONG).show()
            }


        }

    }


    private fun showCamera(){

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
         Snackbar.make(dynamic_per_layout,"camera started",Snackbar.LENGTH_LONG).show()
        }
        else{
           requestPermission()
        }

    }

    private fun requestPermission(){

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CAMERA)){

                 ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.CAMERA),
                     REQUEST_CODE)
                    Log.d(TAG,"inside should show request rationale")

             }
             else{
                 ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.CAMERA),
                     REQUEST_CODE)
             }

            }


    }

    companion object{
        const val TAG ="DynamicPermissionActivi"
        const val REQUEST_CODE :Int = 1
    }

}
