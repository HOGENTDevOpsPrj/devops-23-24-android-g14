package com.hogent.svkapp.domain.entities

import android.util.Log

interface Logger {
    fun debug(tag: String, message: String)
}

class AndroidLogger : Logger {
    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }
}
