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

/**
 * Abstract base ViewModel implementing the MVI (Model-View-Intent) pattern
 *
 * @param State - The UI state data class that represents the current screen state
 * @param Event - Events/actions that can be triggered by the UI (user interactions)
 * @param Effect - One-time side effects like navigation or showing snackbars
 * @param initialState - The starting state when the ViewModel is created
 */

abstract class BaseViewModel<State : Any, Event : Any, Effect : Any>(
    initialState: State
) : ViewModel() {

    // PRIVATE MUTABLE STATE - Only this ViewModel can modify the state
    private val _state = MutableStateFlow(initialState)

    // PUBLIC READ-ONLY STATE - UI can observe this but not modify it directly
    val state: StateFlow<State> = _state.asStateFlow()

    // PRIVATE MUTABLE EVENT FLOW - For internal event handling
    private val _event = MutableSharedFlow<Event>()

    // PUBLIC READ-ONLY EVENT FLOW - UI can observe events (though typically not needed)
    val event: SharedFlow<Event> = _event.asSharedFlow()

    // PRIVATE MUTABLE EFFECT FLOW - For one-time side effects
    private val _effect = MutableSharedFlow<Effect>()
    // PUBLIC READ-ONLY EFFECT FLOW - UI observes this for navigation, snackbars, etc.

    val effect: SharedFlow<Effect> = _effect.asSharedFlow()

    // CONVENIENCE PROPERTY - Easy access to current state value without collecting flow
    protected val currentState: State
        get() = _state.value

    // ERROR HANDLER - Catches and handles any exceptions in coroutines
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    /**
     * PUBLIC METHOD - UI calls this to send events to the ViewModel
     * Launches a coroutine with error handling to process the event
     */
    fun handleEvent(event: Event) {
        viewModelScope.launch(exceptionHandler) {
            onEvent(event) // Calls the abstract method that subclasses must implement
        }
    }

    /**
     * ABSTRACT METHOD - Subclasses must implement this to define how events are handled
     * This is where the business logic for each event type goes
     */
    protected abstract suspend fun onEvent(event: Event)

    /**
     * PROTECTED METHOD - Updates state using a reducer function
     * The reducer takes the current state and returns a new state
     * Example: updateState { copy(isLoading = true) }
     */
    protected fun updateState(reducer: State.() -> State) {
        _state.value = currentState.reducer()
    }

    /**
     * PROTECTED METHOD - Directly sets a new state
     * Less common than updateState, used when you have a complete new state object
     */
    protected fun setState(state: State) {
        _state.value = state
    }

    /**
     * PROTECTED METHOD - Sends a one-time side effect to the UI
     * Examples: navigation, showing toast messages, triggering animations
     */
    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    /**
     * ERROR HANDLING - Override this in subclasses for custom error handling
     * Default behavior just prints the stack trace
     */
    protected open fun handleError(throwable: Throwable) {
        throwable.printStackTrace()
    }

    /**
     * UTILITY METHOD - Launches coroutines with automatic error handling
     * Convenient for any background work that doesn't need to be in onEvent
     */
    protected fun launch(
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(exceptionHandler, block = block)
    }
}