package com.example.movie

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.movie.di.networkModel
import com.example.movie.di.viewModelModule
import com.example.movie.di.workerModule
import com.example.movie.util.MovieUpdateWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit


class MyApp : Application(),Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(networkModel, viewModelModule, workerModule))
            workManagerFactory()
//            modules(listOf(appModule, viewModelModule))
        }
        updateMovie()
    }


    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(KoinWorkerFactory())
            .build()

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
            ExistingPeriodicWorkPolicy.KEEP,
            movieWorkRequest
        )
    }

}