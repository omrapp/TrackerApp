package com.omarayed.dev.trackerapp

import TrackerSDK
import android.app.Application
import android.content.pm.ApplicationInfo

class TrackerApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        TrackerSDK.isEnabled = applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0

    }
}