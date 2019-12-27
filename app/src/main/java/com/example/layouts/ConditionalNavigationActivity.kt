package com.example.layouts

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.models.LogInVm
import org.koin.android.viewmodel.ext.android.viewModel

class ConditionalNavigationActivity : AppCompatActivity() {
    val loginVm by viewModel<LogInVm>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conditional_navigation)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        loginVm.authState.observe(this, androidx.lifecycle.Observer {
            when (it) {
                LogInVm.AuthenticationState.AUTHENTICATED ->
                    menu?.findItem(R.id.signOut)?.isVisible = true

                LogInVm.AuthenticationState.UNAUTHENTICATED ->
                    menu?.findItem(R.id.signOut)?.isVisible = false

                else -> menu?.findItem(R.id.signOut)?.isVisible = false

            }
        })


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.signOut -> {
                loginVm.signOut()
                true
            }

            else -> super.onOptionsItemSelected(item)

        }

    }

}