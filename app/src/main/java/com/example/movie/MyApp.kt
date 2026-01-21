package com.example.movie

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.movie.di.networkModel
import com.example.movie.di.viewModelModule
import com.example.movie.util.MovieUpdateWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(networkModel, viewModelModule))
//            modules(listOf(appModule, viewModelModule))
        }
        updateMovie()
    }

    private fun updateMovie(){
        val movieWorkRequest = PeriodicWorkRequestBuilder<MovieUpdateWorker>(
            4, TimeUnit.HOURS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "MovieDatabaseUpdate",
            ExistingPeriodicWorkPolicy.KEEP, // Keep existing if already scheduled
            movieWorkRequest
        )
    }
}