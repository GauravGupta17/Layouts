package com.example.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DeleteDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Any message")
                .setPositiveButton("update") { _, i ->

                }
                .setNegativeButton("delete") { _, i ->

                }
            builder.create()

        } ?: throw IllegalStateException("activity cant be null")


    }
}