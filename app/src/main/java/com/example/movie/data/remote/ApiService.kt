package com.example.movie.data.remote

import com.example.movie.data.model.MovieDetails
import com.example.movie.data.model.MovieResponseModel
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("3/discover/movie")
    suspend fun getMovies() : MovieResponseModel

    @GET("3/movie/{id}")
    suspend fun getMovieDetail(@Path("id") movieId:String ) : MovieDetails
}