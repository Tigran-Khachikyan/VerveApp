package com.example.vervetaskapp.data.db

import androidx.room.TypeConverter

object StringListConverter {
    private const val KEY = "1as596+..z"

    @TypeConverter
    fun fromString(listString: String?): List<String>? = listString?.split(KEY)

    @TypeConverter
    fun toString(list: List<String>?): String? = list?.joinToString(separator = KEY)
}
