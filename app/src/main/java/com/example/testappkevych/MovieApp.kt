package com.example.testappkevych

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp

class MovieApp:Application() {
    override fun onCreate() {
        super.onCreate()
       // val key = BuildConfig.key

    }
}