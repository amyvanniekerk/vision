package com.example.vision.presentation.viewmodel

import com.example.vision.core.base.BaseViewModel
import com.example.vision.domain.controller.AuthController
import com.example.vision.presentation.state.AuthEffect
import com.example.vision.presentation.state.AuthEvent
import com.example.vision.presentation.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authController: AuthController
) : BaseViewModel<AuthState, AuthEvent, AuthEffect>(AuthState()) {
    
    init {
        observeAuthState()
        checkAuthStatus()
    }
    
    private fun observeAuthState() {
        launch {
            authController.currentUser.collectLatest { user ->
                updateState {
                    copy(
                        isAuthenticated = user != null,
                        user = user
                    )
                }
            }
        }
    }
    
    private fun checkAuthStatus() {
        launch {
            authController.checkAuthStatus()
        }
    }
    
    override suspend fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Login -> handleLogin(event.email, event.password)
            is AuthEvent.Register -> handleRegister(event)
            AuthEvent.Logout -> handleLogout()
            AuthEvent.ClearError -> updateState { copy(error = null) }
            AuthEvent.CheckAuthStatus -> checkAuthStatus()
        }
    }
    
    private suspend fun handleLogin(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            updateState { copy(error = "Email and password are required") }
            return
        }
        
        updateState { copy(isLoading = true, error = null) }
        
        authController.login(email, password).fold(
            onSuccess = { user ->
                updateState { 
                    copy(
                        isLoading = false,
                        isAuthenticated = true,
                        user = user
                    )
                }
                sendEffect(AuthEffect.NavigateToHome)
                sendEffect(AuthEffect.ShowSuccess("Welcome back, ${user.profile.displayName}!"))
            },
            onFailure = { error ->
                updateState { 
                    copy(
                        isLoading = false,
                        error = error.message ?: "Login failed"
                    )
                }
                sendEffect(AuthEffect.ShowError(error.message ?: "Login failed"))
            }
        )
    }
    
    private suspend fun handleRegister(event: AuthEvent.Register) {
        if (event.email.isBlank() || event.password.isBlank() || 
            event.username.isBlank() || event.firstName.isBlank() || 
            event.lastName.isBlank()) {
            updateState { copy(error = "All fields are required") }
            return
        }
        
        updateState { copy(isLoading = true, error = null) }
        
        authController.register(
            email = event.email,
            password = event.password,
            username = event.username,
            firstName = event.firstName,
            lastName = event.lastName
        ).fold(
            onSuccess = { user ->
                updateState { 
                    copy(
                        isLoading = false,
                        isAuthenticated = true,
                        user = user
                    )
                }
                sendEffect(AuthEffect.NavigateToHome)
                sendEffect(AuthEffect.ShowSuccess("Welcome, ${user.profile.displayName}!"))
            },
            onFailure = { error ->
                updateState { 
                    copy(
                        isLoading = false,
                        error = error.message ?: "Registration failed"
                    )
                }
                sendEffect(AuthEffect.ShowError(error.message ?: "Registration failed"))
            }
        )
    }
    
    private suspend fun handleLogout() {
        updateState { copy(isLoading = true) }
        
        authController.logout().fold(
            onSuccess = {
                updateState { AuthState() }
                sendEffect(AuthEffect.NavigateToLogin)
                sendEffect(AuthEffect.ShowSuccess("Logged out successfully"))
            },
            onFailure = { error ->
                updateState { 
                    copy(
                        isLoading = false,
                        error = error.message ?: "Logout failed"
                    )
                }
                sendEffect(AuthEffect.ShowError(error.message ?: "Logout failed"))
            }
        )
    }
}