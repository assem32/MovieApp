package com.example.movie.data.repository

import android.util.Log
import com.example.movie.data.local.Room.MovieDao
import com.example.movie.data.model.MovieEntity
import com.example.movie.data.remote.ApiService
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) {

    suspend fun refreshMovies() {
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
            movieDao.insertMovies(entities)
        } catch (e: Exception) {
            // Log error - the UI will still show the cached data
            Log.e("Repo", "Network fetch failed, using cache: ${e.message}")
        }
    }
}