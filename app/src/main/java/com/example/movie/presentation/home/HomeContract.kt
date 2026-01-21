package com.example.movie.presentation.home

import com.example.movie.data.model.MovieEntity
import com.example.movie.util.UiEvent
import com.example.movie.util.UiSideEffect

class HomeContract {
    data class HomeSata(
        val movieList : List<MovieEntity> = emptyList()
    )

    sealed class HomeEvent : UiEvent{

    }
    sealed class HomeEffect : UiSideEffect{

    }

    interface NavigationAction{
        fun navigateToDetails(id:String)
    }
}