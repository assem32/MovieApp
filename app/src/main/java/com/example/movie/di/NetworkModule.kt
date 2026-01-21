package com.example.movie.di

import androidx.room.Room
import com.example.movie.data.interceptor.AuthInterceptor
import com.example.movie.data.local.AppDatabase
import com.example.movie.data.remote.ApiService
import com.example.movie.data.repository.MovieRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModel = module {
    single { AuthInterceptor(apiKey = "c50f5aa4e7c95a2a553d29b81aad6dd0") }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(ApiService::class.java)
    }

    single { Room.databaseBuilder(get(), AppDatabase::class.java, "movies.db").build() }
    single { get<AppDatabase>().movieDao() }
    single { MovieRepository(get(), get()) }

}
