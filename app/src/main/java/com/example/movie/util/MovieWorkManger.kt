package com.example.movie.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movie.data.repository.MovieRepository

class MovieUpdateWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val repository: MovieRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            repository.getMovies()
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }
}