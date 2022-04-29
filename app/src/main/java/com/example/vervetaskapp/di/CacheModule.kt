package com.example.vervetaskapp.di

import com.example.vervetaskapp.data.db.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val cacheModule = module {
    single { AppDatabase.create(androidApplication()) }
    single { get<AppDatabase>().taskDao }
    single { get<AppDatabase>().projectDao }
}