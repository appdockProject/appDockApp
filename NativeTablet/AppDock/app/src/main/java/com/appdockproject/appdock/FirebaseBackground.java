package com.appdockproject.appdock;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jangerhard on 04-May-17.
 */

public class FirebaseBackground extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Setup firebase to cache data locally
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
