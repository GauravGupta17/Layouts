package com.example.layouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adapters.TabViewPagerAdapter
import com.example.fragments.ContactListFragment
import com.example.fragments.FrequntsFragment
import com.example.fragments.RecentFragment
import kotlinx.android.synthetic.main.activity_tab_view_pager.*

class TabViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_view_pager)
        setSupportActionBar(toolbar)

        val adapter = TabViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FrequntsFragment(),"Frequent")
        adapter.addFragment(RecentFragment(),"Recent")
        adapter.addFragment(ContactListFragment(),"List")
        tabPager.adapter = adapter
        tabs.setupWithViewPager(tabPager)



    }

}