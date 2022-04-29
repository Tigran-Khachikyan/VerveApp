package com.example.vervetaskapp

import android.app.Application
import com.example.vervetaskapp.di.cacheModule
import com.example.vervetaskapp.di.presentationModule
import com.example.vervetaskapp.di.repositoriesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    repositoriesModule,
                    cacheModule,
                    presentationModule
                )
            )
        }
    }
}
