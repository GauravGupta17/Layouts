package com.example.database

import com.google.firebase.database.FirebaseDatabase

class FirDatabse {

    companion object {

        @Volatile
        private var INSTANCE: FirebaseDatabase? = null

        fun getDatabase(): FirebaseDatabase {

            val tempInstance = INSTANCE

            if (tempInstance != null) {

                return tempInstance
            }
            synchronized(this) {

                val instance = FirebaseDatabase.getInstance()
                FirebaseDatabase.getInstance().setPersistenceEnabled(true)

                INSTANCE = instance

                return instance
            }


        }


    }


}