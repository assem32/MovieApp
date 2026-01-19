package com.example.movie

import android.app.Application
import com.example.movie.di.networkModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(networkModel))
//            modules(listOf(appModule, viewModelModule))
        }
    }
}