package com.example.layouts

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_hid_and_show_fragment.*

class FragmentsHidAndShowActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val addSongFragment = AddSongFragment2()
    private val userProfileFragment = UserProfileFragment2()
    private var active: Fragment = addSongFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hid_and_show_fragment)

        val fm = supportFragmentManager
        fm.beginTransaction().add(R.id.placeHolderBtm, userProfileFragment)
            .hide(userProfileFragment).commit()
        fm.beginTransaction().add(R.id.placeHolderBtm, addSongFragment).commit()
        btmNavSongs.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.userProfileFragment -> {
                supportFragmentManager.beginTransaction().hide(active).show(userProfileFragment)
                    .commit()
                active = userProfileFragment
                true
            }
            R.id.songListFragment -> {
                supportFragmentManager.beginTransaction().hide(active).show(addSongFragment)
                    .commit()
                active = addSongFragment
                true
            }
            else -> false
        }

    }

}