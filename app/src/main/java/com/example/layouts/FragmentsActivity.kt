package com.example.layouts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.fragments.DetailFragment
import com.example.fragments.TitlesFragments
import com.example.utilis.Connector
import kotlinx.android.synthetic.main.activity_fragments.*

class FragmentsActivity : AppCompatActivity(), Connector {

    var dualPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragments)


        supportFragmentManager.beginTransaction().commit()

        dualPane = flDetails?.visibility == View.VISIBLE



        Log.d("Fragments Activity", "$dualPane")


        if (dualPane) {

            val detailFragment = DetailFragment()

            supportFragmentManager.beginTransaction().replace(R.id.flDetails, detailFragment)
                .commit()
            Log.d("Fragments Activity", "Sucess")

        }

    }

    override fun DisplayDetails(detail: String) {

        if (dualPane) {

            val DetailFragment =
                supportFragmentManager.findFragmentById(R.id.flDetails) as DetailFragment
                DetailFragment.DisplayDetail(detail)


        } else {
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("DETAIL", detail)

            }
            startActivity(intent)

        }


    }

}


