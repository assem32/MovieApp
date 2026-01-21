package com.example.movie.util

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movie.data.repository.MovieRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MovieUpdateWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    private val repository: MovieRepository by inject()

    override suspend fun doWork(): Result {
        return try {
            repository.getMovies()
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }
}