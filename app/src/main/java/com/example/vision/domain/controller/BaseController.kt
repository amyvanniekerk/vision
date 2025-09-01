package com.example.vision.domain.controller

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseController<State : Any>(
    initialState: State
) {
    protected val controllerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()
    
    protected val currentState: State
        get() = _state.value
    
    protected fun updateState(reducer: State.() -> State) {
        _state.value = currentState.reducer()
    }
    
    protected fun setState(state: State) {
        _state.value = state
    }
    
    protected fun launch(
        block: suspend CoroutineScope.() -> Unit
    ) {
        controllerScope.launch(block = block)
    }
    
    protected suspend fun <T> withIO(
        block: suspend CoroutineScope.() -> T
    ): T = withContext(Dispatchers.IO, block)
    
    protected suspend fun <T> withMain(
        block: suspend CoroutineScope.() -> T
    ): T = withContext(Dispatchers.Main, block)
    
    open fun onCleared() {
        controllerScope.cancel()
    }
}

interface Controller {
    fun onCleared()
}