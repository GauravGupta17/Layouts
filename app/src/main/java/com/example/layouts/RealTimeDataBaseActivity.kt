package com.example.layouts

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.database.FirDatabse
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_real_time_database.*

class RealTimeDataBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time_database)

        val db = FirDatabse.getDatabase()

        val dbRef = db.getReference("anything")
        btnRTSubmit.setOnClickListener {

            val name = etRealTimeName.text.toString()
            val area = etRealTimeArea.text.toString()
            Log.d(TAG, "$name $area")
            dbRef.setValue(Users(name, area))

        }

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val value = dataSnapshot.getValue(Users::class.java)
                val name = value?.name
                val words = value?.words
                Log.d(TAG, "$value")
                tvUserDataRT.text = "$name $words"

            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        })


    }

    data class Users(var name: String = " ", var words: String = " ")

    companion object {
        const val TAG = "RealTimeDataBaseActivty"
    }


}