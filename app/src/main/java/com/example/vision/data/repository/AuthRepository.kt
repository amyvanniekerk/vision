package com.example.vision.data.repository

import com.example.vision.data.model.AuthCredentials
import com.example.vision.data.model.Gender
import com.example.vision.data.model.RegistrationData
import com.example.vision.data.model.User
import com.example.vision.data.model.UserProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor() {
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: Flow<User?> = _currentUser.asStateFlow()
    
    suspend fun login(credentials: AuthCredentials): Result<User> {
        return try {
            delay(1000)
            
            if (credentials.email == "test@example.com" && credentials.password == "password") {
                val user = createMockUser(credentials.email)
                _currentUser.value = user
                Result.success(user)
            } else {
                Result.failure(Exception("Invalid credentials"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun register(registrationData: RegistrationData): Result<User> {
        return try {
            delay(1000)
            
            val user = User(
                id = UUID.randomUUID().toString(),
                email = registrationData.email,
                username = registrationData.username,
                profile = UserProfile(
                    firstName = registrationData.firstName,
                    lastName = registrationData.lastName,
                    displayName = "${registrationData.firstName} ${registrationData.lastName}"
                ),
                createdAt = Date(),
                isActive = true
            )
            
            _currentUser.value = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun logout(): Result<Unit> {
        return try {
            delay(500)
            _currentUser.value = null
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getCurrentUser(): User? {
        return _currentUser.value
    }
    
    suspend fun updateProfile(profile: UserProfile): Result<User> {
        return try {
            delay(1000)
            val currentUser = _currentUser.value ?: throw Exception("No user logged in")
            val updatedUser = currentUser.copy(profile = profile)
            _currentUser.value = updatedUser
            Result.success(updatedUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun createMockUser(email: String): User {
        return User(
            id = "admin_001",
            email = email,
            username = "admin",
            role = com.example.vision.data.model.UserRole.ADMIN,
            profile = UserProfile(
                firstName = "Amy",
                lastName = "Van Niekerk",
                displayName = "Amy",
                bio = "System Administrator",
                gender = Gender.FEMALE
            ),
            createdAt = Date(),
            lastLoginAt = Date(),
            isActive = true,
            isEmailVerified = true
        )
    }
}