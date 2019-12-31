package com.example.layouts

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dialogs.DeleteDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        btnShowDialog.setOnClickListener {
            DeleteDialog().show(supportFragmentManager,"dialog")
        }

        btnAlertDialog.setOnClickListener {
            getAlertDialog().show()

        }

    }

    private fun getAlertDialog():Dialog{

        val builder = AlertDialog.Builder(this)

       return builder.apply {

           setTitle("title")
           setMessage("Are you sure")
            setPositiveButton("ok") {
                dialog,id->
                Snackbar.make(dialog_layout,"ok clicked",Snackbar.LENGTH_LONG).show()
            }
            setNegativeButton("cancel") { dialog, i ->
                Snackbar.make(dialog_layout,"cancel clicked",Snackbar.LENGTH_LONG).show()
            }
        }.create()


    }



}