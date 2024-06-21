package com.rahul.bankregistration.utility

import android.app.Activity
import android.app.Application

class MyApplication : Application() {
    var currentActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()
    }
}

val Application.currentActivity: Activity?
    get() = (this as MyApplication).currentActivity
