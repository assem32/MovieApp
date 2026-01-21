package com.example.movie.presentation.home

import com.example.movie.data.model.MovieEntity
import com.legsy.courses.core.base.UiEvent
import com.legsy.courses.core.base.UiSideEffect

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