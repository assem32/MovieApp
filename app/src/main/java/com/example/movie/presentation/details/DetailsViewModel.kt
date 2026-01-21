package com.example.movie.presentation.details


import com.example.movie.data.repository.MovieRepository
import com.example.movie.util.BaseViewModel
import com.example.movie.util.Result

class DetailsViewModel(
    private val repository: MovieRepository
) : BaseViewModel<DetailsState.DetailsEvent, DetailsState.State>() {
    override fun setInitState(): DetailsState.State = DetailsState.State()

    override fun handleEvent(event: DetailsState.DetailsEvent) {
        when (event) {
            is DetailsState.DetailsEvent.OnInitScreen -> {
                getMovieDetails(event.movieId)
            }
        }
    }

    private fun getMovieDetails(id: String) {
        execute(
            run = {
                repository.getMovieDetails(id)
            }
        ) { result ->
            when (result) {
                is Result.Success -> {
                    setState {
                        copy(
                            detailsModel = result.data
                        )
                    }
                }

                is Result.Error -> {
                    setEffect { DetailsState.DetailsSideEffect.GetDetailError }
                }
            }

        }
    }
}