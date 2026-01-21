package com.example.movie.data.repository

import android.util.Log
import com.example.movie.data.local.MovieDao
import com.example.movie.data.model.MovieEntity
import com.example.movie.data.remote.ApiService

class MovieRepository(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) {

        suspend fun getMovies(): List<MovieEntity> {
            try {
                val response = apiService.getMovies()
                val entities = response.results.map { result ->
                    MovieEntity(
                        id = result.id,
                        title = result.title,
                        overview = result.overview,
                        poster_path = result.poster_path,
                        release_date = result.release_date,
                        vote_average = result.vote_average,
                        genre_ids = result.genre_ids
                    )
                }
                // 2. Save fresh data to Database
                movieDao.insertMovies(entities)

            } catch (e: Exception) {
                // Network failed, but we don't return here!
                Log.e("Repo", "Network fetch failed: ${e.message}")
            }

            // 3. ALWAYS return from the DAO (Single Source of Truth)
            return movieDao.getAllMovies()
        }
    }