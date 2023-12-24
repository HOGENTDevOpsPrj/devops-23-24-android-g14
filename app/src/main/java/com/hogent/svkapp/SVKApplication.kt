package com.hogent.svkapp

import android.app.Application
import com.hogent.svkapp.data.AppContainer
import com.hogent.svkapp.data.DefaultAppContainer

/**
 * The main application class.
 *
 * @property container The [AppContainer] used for dependency injection.
 */
class SVKApplication : Application() {
    lateinit var container: AppContainer

    /**
     * Creates the [AppContainer].
     */
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}