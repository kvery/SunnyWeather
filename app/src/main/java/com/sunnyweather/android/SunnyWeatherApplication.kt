package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 *@author kvery
 *@date 2021-09-04 20:09
 */
class SunnyWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val KEY = "b87c31e10d7e4203a9a29eda5a26a4bd"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}