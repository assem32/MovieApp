package com.example.movie.data.remote

import com.example.movie.data.model.MovieResponseModel
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET

interface ApiService {

    @GET("3/discover/movie")
    suspend fun getMovies() : MovieResponseModel
}