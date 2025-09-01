package com.example.vision.presentation.state

import com.example.vision.data.model.User

data class AuthState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val user: User? = null,
    val error: String? = null
)

sealed class AuthEvent {
    data class Login(val email: String, val password: String) : AuthEvent()
    data class Register(
        val email: String,
        val password: String,
        val username: String,
        val firstName: String,
        val lastName: String
    ) : AuthEvent()
    object Logout : AuthEvent()
    object ClearError : AuthEvent()
    object CheckAuthStatus : AuthEvent()
}

sealed class AuthEffect {
    object NavigateToHome : AuthEffect()
    object NavigateToLogin : AuthEffect()
    object NavigateToProfile : AuthEffect()
    data class ShowError(val message: String) : AuthEffect()
    data class ShowSuccess(val message: String) : AuthEffect()
}