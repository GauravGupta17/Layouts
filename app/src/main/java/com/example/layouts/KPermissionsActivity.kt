package com.example.layouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fondesa.kpermissions.extension.onAccepted
import com.fondesa.kpermissions.extension.onDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_dynamic_permission.*


class KPermissionsActivity : AppCompatActivity(){

    private val request by lazy {
        permissionsBuilder(android.Manifest.permission.CAMERA).build().apply {
            onAccepted {
                Snackbar.make(dynamic_per_layout, "Granted", Snackbar.LENGTH_LONG).show()
            }
            onDenied {
                Snackbar.make(dynamic_per_layout, "Denied", Snackbar.LENGTH_LONG).show()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_permission)

        btnTakePermission.setOnClickListener {
            request.send()
        }

    }
}