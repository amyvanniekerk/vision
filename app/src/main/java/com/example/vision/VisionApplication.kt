package com.example.vision

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VisionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}