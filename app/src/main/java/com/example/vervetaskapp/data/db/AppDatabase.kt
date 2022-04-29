package com.example.vervetaskapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vervetaskapp.models.Project
import com.example.vervetaskapp.models.Task

@Database(entities = [Project::class, Task::class], version = 1)
@TypeConverters(DateConverter::class, StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val projectDao: ProjectDao
    abstract val taskDao: TaskDao

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "App_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}