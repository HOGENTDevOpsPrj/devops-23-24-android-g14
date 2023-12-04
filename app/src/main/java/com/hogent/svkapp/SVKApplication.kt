package com.hogent.svkapp

import android.app.Application
import com.hogent.svkapp.data.AppContainer
import com.hogent.svkapp.data.DefaultAppContainer

class SVKApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}