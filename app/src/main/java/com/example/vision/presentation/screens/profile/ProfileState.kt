package com.example.vision.presentation.screens.profile

import com.example.vision.data.model.UserProfile

data class ProfileState(
    val isLoading: Boolean = false,
    val profile: UserProfile? = null,
    val isEditing: Boolean = false,
    val error: String? = null,
    val updateSuccess: Boolean = false
)

sealed class ProfileEvent {
    object LoadProfile : ProfileEvent()
    object StartEditing : ProfileEvent()
    object CancelEditing : ProfileEvent()
    data class UpdateProfile(val profile: UserProfile?) : ProfileEvent()
    data class UpdateAvatar(val avatarUrl: String) : ProfileEvent()
    object ClearError : ProfileEvent()
}

sealed class ProfileEffect {
    data class ShowError(val message: String) : ProfileEffect()
    data class ShowSuccess(val message: String) : ProfileEffect()
    object NavigateBack : ProfileEffect()
}