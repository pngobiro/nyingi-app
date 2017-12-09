package com.ngobiro.biroproperties

import android.app.Application
import com.parse.ParseACL
import com.parse.ParseUser
import com.parse.Parse
import com.parse.ParseAnalytics


class StarterApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        // Enable Local Datastore.
        // Parse.enableLocalDatastore(this)
        // Add your initialization code here

        Parse.initialize(this)
        ParseUser.enableAutomaticUser()
        val defaultACL = ParseACL()
        // Optionally enable public read access.
        defaultACL.publicReadAccess = true
        defaultACL.publicWriteAccess = false
        ParseACL.setDefaultACL(defaultACL, true)
    }




}