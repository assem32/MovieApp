package com.example.movie.util

sealed class Result <out D,out E>{
    data class Success<out D>(val data :D):Result<D, Nothing>()
    data class Error <out E> (val error :E):Result<Nothing, E>()
}



inline fun <T , E ,R> Result<T,E>.fold(
    onSuccess : (T)->R,
    onError : (E)->R
):R{
    return when(this) {
        is Result.Success -> onSuccess(data)
        is Result.Error -> onError(error)
    }
}