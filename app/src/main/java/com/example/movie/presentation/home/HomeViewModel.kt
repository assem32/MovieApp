package com.example.movie.presentation.home

import com.example.movie.data.repository.MovieRepository
import com.example.movie.util.BaseViewModel
import kotlinx.coroutines.Dispatchers

class HomeViewModel (
    val repository: MovieRepository
): BaseViewModel<HomeContract.HomeEvent,HomeContract.HomeSata>(){
    override fun setInitState(): HomeContract.HomeSata = HomeContract.HomeSata()

    override fun handleEvent(event: HomeContract.HomeEvent) {
        TODO("Not yet implemented")
    }

    init {
        getMovies()
    }

    private fun getMovies(){
         execute(
             dispatcher = Dispatchers.IO,
             run = {
                 repository.getMovies()
             }
         ){
             setState {
                 copy(
                     movieList = it
                 )
             }
         }
    }
}