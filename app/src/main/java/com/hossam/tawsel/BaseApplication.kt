package com.hossam.tawsel

import android.app.Application
import com.hossam.tawsel.core.SharedPref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SharedPref.init(this)
    }

}