package com.example.movie.di

import com.example.movie.util.MovieUpdateWorker
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workerModule = module {
    worker { MovieUpdateWorker(get(), get()) }
}