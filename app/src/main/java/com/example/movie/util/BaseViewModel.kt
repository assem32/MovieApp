package com.example.movie.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<Event : UiEvent, State> : ViewModel() {



    private val initState: UiState<State> by lazy { UiState(data = setInitState()) }

    abstract fun setInitState(): State

    private val _state = MutableStateFlow<UiState<State>>(initState)
    val state = _state.asStateFlow()

    private val coroutineException =
        CoroutineExceptionHandler { _, throwable -> handleCoroutineException(throwable) }

    private val viewModelScopeWithHandler = viewModelScope + coroutineException

    private val _event = MutableSharedFlow<Event>()

    private val _effect: Channel<UiSideEffect> = Channel()
    val effect = _effect.receiveAsFlow()
    init {
        viewModelScope.launch {
            _event.collect { event ->
                handleEvent(event)   // 👈 forward every event to subclass
            }
        }
    }


    fun setEffect(builder: () -> UiSideEffect) {
        val effectValue = builder()
        _effect.trySend(effectValue).isSuccess // Log or handle failure to send effect
    }

    fun setState(reducer: State.() -> State) {
        _state.update {
            it.copy(data = reducer(it.data))
        }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    fun <T> execute(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        run: suspend ()->T,
        onResult: (T)->Unit
    ){
        launchCoroutine(dispatcher){
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val result = run()

            onResult(result)
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun launchCoroutine(
        dispatcher: CoroutineContext,
        block : suspend CoroutineScope.()-> Unit
    ): Job{
        return viewModelScopeWithHandler.launch (dispatcher){
            block()
        }
    }

    abstract fun handleEvent(event: Event)

    protected open fun handleCoroutineException(exception: Throwable) {
        _state.update { it.copy(isLoading = false) }
    }

}

@Composable
fun Flow<UiSideEffect>.OnEffect(action: (effect: UiSideEffect) -> Unit) {
    LaunchedEffect (Unit) {
        onEach {
            action(it)
        }.collect()
    }
}