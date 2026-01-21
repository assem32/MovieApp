package com.example.movie.data.repository

import com.example.movie.data.local.MovieDao
import com.example.movie.data.model.MovieDetails
import com.example.movie.data.model.MovieEntity
import com.example.movie.data.remote.ApiService
import com.example.movie.util.EventBus
import com.example.movie.util.Result
import com.example.movie.util.ShowBottomSheet

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
            EventBus.publish(ShowBottomSheet("No Internet connection"))
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