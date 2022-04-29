package com.example.vervetaskapp.data.db

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? = dateLong?.let { Date(it) }

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time
}