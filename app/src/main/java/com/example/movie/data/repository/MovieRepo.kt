package com.example.movie.data.repository

import android.util.Log
import com.example.movie.data.local.MovieDao
import com.example.movie.data.model.MovieDetails
import com.example.movie.data.model.MovieEntity
import com.example.movie.data.remote.ApiService
import com.example.movie.util.Result

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
            movieDao.insertMovies(entities)
        } catch (e: Exception) {
            Log.e("Repo", "Network fetch failed: ${e.message}")
        }
        return movieDao.getAllMovies()
    }

    suspend fun getMovieDetails(movieId:String):  Result<MovieDetails,String>{
        try {
            val response = apiService.getMovieDetail(movieId = movieId)
            return Result.Success(response)

        }catch (e: Exception){
            return Result.Error(e.message+"")
        }
    }
}