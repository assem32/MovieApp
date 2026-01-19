package com.legsy.courses.core.base

data class UiState<T>(
    val isLoading : Boolean = false,
    val data : T
)
