package com.example.layouts

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapters.ParentContactAdapter
import com.example.models.ContactInfo
import kotlinx.android.synthetic.main.acivity_recycler.*
import kotlinx.android.synthetic.main.activity_nestedrv.*

class ContactListActivity : AppCompatActivity() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nestedrv)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val traineeList = arrayListOf(
            ContactInfo(
                "Gaurav",
                "4454545455",
                HAS_IMAGE,
                R.drawable.ic_perm_identity_black_24dp,
                TRAINEE
            ),
            ContactInfo(
                "Shyam",
                "4454545455",
                HAS_IMAGE,
                R.drawable.ic_perm_identity_black_24dp,
                TRAINEE
            ),
            ContactInfo("Shiva", "4454545455", NO_IMAGE, null, TRAINEE)
        )

        val mentorList = arrayListOf(
            ContactInfo("krishna", "784445454", NO_IMAGE, null, MENTORS),
            ContactInfo("Arjun", "784445454", NO_IMAGE, null, MENTORS),
            ContactInfo(
                "Yudister",
                "784445454",
                HAS_IMAGE,
                R.drawable.ic_perm_identity_black_24dp,
                MENTORS
            )
        )


        val othersList = arrayListOf(
            ContactInfo(
                "Ram",
                "4454545455",
                HAS_IMAGE,
                R.drawable.ic_perm_identity_black_24dp,
                OTHERS
            ),
            ContactInfo(
                "Ram",
                "4454545455",
                HAS_IMAGE,
                R.drawable.ic_perm_identity_black_24dp,
                OTHERS
            )
        )


        val contactList = mutableMapOf(
            TRAINEE to traineeList,
            MENTORS to mentorList,
            OTHERS to othersList

        )






        viewManager = LinearLayoutManager(this)

        viewAdapter = ParentContactAdapter(contactList)

        rvParent.apply {

            layoutManager = viewManager
            adapter = viewAdapter

        }


    }







    companion object {
        const val TRAINEE = 1
        const val MENTORS = 2
        const val OTHERS = 0
        const val HAS_IMAGE = 1
        const val NO_IMAGE = 0

    }

}
