package com.example.vision.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : Any, Event : Any, Effect : Any>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()

    protected val currentState: State
        get() = _state.value

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    fun handleEvent(event: Event) {
        viewModelScope.launch(exceptionHandler) {
            onEvent(event)
        }
    }

    protected abstract suspend fun onEvent(event: Event)

    protected fun updateState(reducer: State.() -> State) {
        _state.value = currentState.reducer()
    }

    protected fun setState(state: State) {
        _state.value = state
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    protected open fun handleError(throwable: Throwable) {
        throwable.printStackTrace()
    }

    protected fun launch(
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(exceptionHandler, block = block)
    }
}