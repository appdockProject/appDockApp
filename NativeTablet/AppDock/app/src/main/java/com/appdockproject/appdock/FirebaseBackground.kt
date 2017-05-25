package com.appdockproject.appdock

import android.app.Application

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by jangerhard on 04-May-17.
 */

class FirebaseBackground : Application() {

    override fun onCreate() {
        super.onCreate()

        // Setup firebase to cache data locally
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
