package com.example.layouts

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapters.ActivityAdapter
import com.example.utilis.ItemTouchHelperCallBack
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.acivity_recycler.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var viewAdapter: ActivityAdapter? = null
    var activitylist = arrayListOf<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        activitylist = arrayListOf(
            "Motion Layout",
            "Image Filter Motion Layout",
            "Recycler View ",
            "Animations | Activity Lifecycle",
            "Fragments",
            "Saving :- Key Value Pairs",
            "Saving :-Files | LifeCycle Aware concept",
            "ViewModel",
            "Form Fragment",
            "User Info | Room DB"
            , "Playlist Sharing App | Room DB",
            "Content Provider",
            "School App",
            "FireBase LogIn",
            "FireBase Email Login",
            "FireBase Storage",
            "FireBase Messaging",
            "FireStore",
            "RealTime DB",
            "View Pager Basic",
            "View Pager Tab",
            "View Pager 2",
            "Services",
            "WorkManager",
            "Retrofit"
        ,   "Navigation Basics",
            "Cond Navigation",
            "Dynamic permission",
            "Dialogs",
            "Notification",
            "Kpermissions",
            "Retrofit",
            "Hide & Show Frag"
        )

        viewAdapter = ActivityAdapter(activitylist)


        mainRV.apply {

            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = viewAdapter

        }
        viewAdapter?.let { ItemTouchHelper(ItemTouchHelperCallBack(it)).attachToRecyclerView(mainRV) }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    viewAdapter?.filter?.filter(newText)


                    return true
                }


            }

            )

        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        R.id.settings -> {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }

        else -> super.onOptionsItemSelected(item)
    }


}
