package com.example.movie.util

data class UiState<T>(
    val isLoading : Boolean = false,
    val data : T
)
