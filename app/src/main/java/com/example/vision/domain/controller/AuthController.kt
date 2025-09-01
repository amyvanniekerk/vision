package com.example.vision.domain.controller

import com.example.vision.data.model.AuthCredentials
import com.example.vision.data.model.RegistrationData
import com.example.vision.data.model.User
import com.example.vision.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthController @Inject constructor(
    private val authRepository: AuthRepository
) : BaseController<AuthControllerState>(AuthControllerState()) {
    
    val currentUser: Flow<User?> = authRepository.currentUser
    
    suspend fun login(email: String, password: String): Result<User> {
        setState(currentState.copy(isLoading = true, error = null))
        
        val result = authRepository.login(
            AuthCredentials(email = email, password = password)
        )
        
        result.fold(
            onSuccess = { user ->
                setState(currentState.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    user = user
                ))
            },
            onFailure = { error ->
                setState(currentState.copy(
                    isLoading = false,
                    error = error.message
                ))
            }
        )
        
        return result
    }
    
    suspend fun register(
        email: String,
        password: String,
        username: String,
        firstName: String,
        lastName: String
    ): Result<User> {
        setState(currentState.copy(isLoading = true, error = null))
        
        val result = authRepository.register(
            RegistrationData(
                email = email,
                password = password,
                username = username,
                firstName = firstName,
                lastName = lastName
            )
        )
        
        result.fold(
            onSuccess = { user ->
                setState(currentState.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    user = user
                ))
            },
            onFailure = { error ->
                setState(currentState.copy(
                    isLoading = false,
                    error = error.message
                ))
            }
        )
        
        return result
    }
    
    suspend fun logout(): Result<Unit> {
        setState(currentState.copy(isLoading = true))
        
        val result = authRepository.logout()
        
        result.fold(
            onSuccess = {
                setState(AuthControllerState())
            },
            onFailure = { error ->
                setState(currentState.copy(
                    isLoading = false,
                    error = error.message
                ))
            }
        )
        
        return result
    }
    
    suspend fun checkAuthStatus() {
        val user = authRepository.getCurrentUser()
        setState(currentState.copy(
            isAuthenticated = user != null,
            user = user
        ))
    }
}

data class AuthControllerState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val user: User? = null,
    val error: String? = null
)