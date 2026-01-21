package com.example.movie.presentation.details

import com.example.movie.data.model.MovieDetails
import com.legsy.courses.core.base.UiEvent

class DetailsState {
    data class State(
        val detailsModel : MovieDetails? =null
    )

    sealed class DetailsEvent : UiEvent{
        data class OnInitScreen(val movieId : String) : DetailsEvent()
    }
}