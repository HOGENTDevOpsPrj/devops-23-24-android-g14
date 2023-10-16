package com.hogent.svkapp.main.util

import android.util.Log

interface Logger {
    fun debug(tag: String, message: String)
}

class AndroidLogger : Logger {
    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }
}

class NoOpLogger : Logger {
    override fun debug(tag: String, message: String) {
        // Do nothing for tests
    }
}
