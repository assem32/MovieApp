package com.example.movie.presentation.details

import com.example.movie.data.model.MovieDetails
import com.example.movie.util.UiEvent
import com.example.movie.util.UiSideEffect

class DetailsState {
    data class State(
        val detailsModel : MovieDetails? =null
    )

    sealed class DetailsSideEffect: UiSideEffect{
        object GetDetailError : DetailsSideEffect()
    }

    sealed class DetailsEvent : UiEvent{
        data class OnInitScreen(val movieId : String) : DetailsEvent()
    }
}