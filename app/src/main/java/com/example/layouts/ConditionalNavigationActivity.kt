package com.example.layouts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.fragments.AddSongFragment
import com.example.fragments.LoginFragment
import com.example.fragments.SongListFragment
import com.example.models.LogInVm
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_conditional_navigation.*
import kotlinx.android.synthetic.main.text_view.*
import org.koin.android.viewmodel.ext.android.viewModel

class ConditionalNavigationActivity : AppCompatActivity(){

    private val loginVm by viewModel<LogInVm>()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conditional_navigation)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        btmNavSongs.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        when (intent?.action) {
            Intent.ACTION_SEND -> {
                val amount = intent.getStringExtra(Intent.EXTRA_TEXT)
                navController.navigate(CondnavgraphDirections.loginFragmentSignout(amount))
            }

        }

        loginVm.authState.observe(this, Observer {
            if (it == LogInVm.AuthenticationState.SIGNOUT) {
                btmNavSongs.visibility = View.GONE
            }

        })


    }

    companion object {
        const val TAG = "condinationalNav"
    }


}