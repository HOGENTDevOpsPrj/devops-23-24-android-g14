package com.hogent.svkapp.data.sources.roomDataBase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Converters for the Room database.
 */
class Converters {
    /**
     * Converts a [String] to a [List] of [String]s.
     */
    @TypeConverter
    fun fromStringToList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    /**
     * Converts a [List] of [String]s to a [String].
     */
    @TypeConverter
    fun fromListToString(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}