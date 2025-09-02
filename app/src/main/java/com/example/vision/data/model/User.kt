package com.example.vision.data.model

import java.util.Date

data class User(
    val id: String,
    val email: String,
    val username: String,
    val profile: UserProfile,
    val role: UserRole = UserRole.CUSTOMER,
    val employeeData: EmployeeData? = null,
    val createdAt: Date = Date(),
    val lastLoginAt: Date? = null,
    val isActive: Boolean = true,
    val isEmailVerified: Boolean = false
)

data class UserProfile(
    val firstName: String,
    val lastName: String,
    val displayName: String,
    val bio: String? = null,
    val avatarUrl: String? = null,
    val phoneNumber: String? = null,
    val dateOfBirth: Date? = null,
    val gender: Gender? = null,
    val location: Location? = null,
    val preferences: UserPreferences = UserPreferences(),
    val eyeData: List<EyeData> = emptyList()
)

data class Location(
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val zipCode: String? = null
)

data class UserPreferences(
    val theme: Theme = Theme.SYSTEM,
    val language: String = "en",
    val notificationsEnabled: Boolean = true,
    val emailNotifications: Boolean = true,
    val pushNotifications: Boolean = true
)

enum class Gender {
    MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY
}

enum class Theme {
    LIGHT, DARK, SYSTEM
}

enum class UserRole {
    CUSTOMER,
    EMPLOYEE,
    ADMIN
}

data class EmployeeData(
    val employeeId: String,
    val department: String,
    val position: String,
    val permissions: List<Permission> = emptyList(),
    val assignedCustomers: List<String> = emptyList()
)

enum class Permission {
    VIEW_CUSTOMER_PROFILES,
    EDIT_CUSTOMER_PROFILES,
    VIEW_EYE_PHOTOS,
    UPLOAD_EYE_PHOTOS,
    MANAGE_APPOINTMENTS,
    GENERATE_REPORTS
}

data class AuthCredentials(
    val email: String,
    val password: String
)

data class RegistrationData(
    val email: String,
    val password: String,
    val username: String,
    val firstName: String,
    val lastName: String
)