package com.hogent.svkapp.domain.entities

import android.util.Log

/**
 * A logger.
 */
interface Logger {
    /**
     * Logs a message on the debug level.
     *
     * @param tag the tag to log the message with.
     * @param message the message to log.
     */
    fun debug(tag: String, message: String)
}

/**
 * An [AndroidLogger] that uses the [Log] class to log messages.
 */
class AndroidLogger : Logger {
    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }
}
