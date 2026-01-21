package com.example.movie.util

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.coroutines.coroutineContext

object EventBus{
    private val _events = MutableSharedFlow<UiSideEffect>(
        replay = 1
    )
    val event = _events.asSharedFlow()


    suspend fun publish(event: UiSideEffect) {
        _events.emit(event)
    }

    suspend inline fun <reified T : UiSideEffect> subscribe(crossinline onEvent: (T) -> Unit) {
        event.filterIsInstance<T>()
            .collectLatest { event->
                coroutineContext.ensureActive()
                onEvent(event)
            }
    }
}