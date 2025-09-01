package com.example.vision.presentation.viewmodel

import com.example.vision.core.base.BaseViewModel
import com.example.vision.data.model.UserProfile
import com.example.vision.data.repository.AuthRepository
import com.example.vision.presentation.state.ProfileEffect
import com.example.vision.presentation.state.ProfileEvent
import com.example.vision.presentation.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<ProfileState, ProfileEvent, ProfileEffect>(ProfileState()) {
    
    init {
        loadProfile()
    }
    
    override suspend fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.LoadProfile -> loadProfile()
            ProfileEvent.StartEditing -> updateState { copy(isEditing = true) }
            ProfileEvent.CancelEditing -> updateState { copy(isEditing = false) }
            is ProfileEvent.UpdateProfile -> updateProfile(event.profile)
            is ProfileEvent.UpdateAvatar -> updateAvatar(event.avatarUrl)
            ProfileEvent.ClearError -> updateState { copy(error = null) }
        }
    }
    
    private fun loadProfile() {
        launch {
            updateState { copy(isLoading = true) }
            
            val user = authRepository.getCurrentUser()
            if (user != null) {
                updateState {
                    copy(
                        isLoading = false,
                        profile = user.profile
                    )
                }
            } else {
                updateState {
                    copy(
                        isLoading = false,
                        error = "No user logged in"
                    )
                }
                sendEffect(ProfileEffect.ShowError("Please login to view profile"))
            }
        }
    }
    
    private suspend fun updateProfile(profile: UserProfile) {
        updateState { copy(isLoading = true) }
        
        authRepository.updateProfile(profile).fold(
            onSuccess = { user ->
                updateState {
                    copy(
                        isLoading = false,
                        profile = user.profile,
                        isEditing = false,
                        updateSuccess = true
                    )
                }
                sendEffect(ProfileEffect.ShowSuccess("Profile updated successfully"))
            },
            onFailure = { error ->
                updateState {
                    copy(
                        isLoading = false,
                        error = error.message
                    )
                }
                sendEffect(ProfileEffect.ShowError(error.message ?: "Failed to update profile"))
            }
        )
    }
    
    private suspend fun updateAvatar(avatarUrl: String) {
        val currentProfile = currentState.profile ?: return
        val updatedProfile = currentProfile.copy(avatarUrl = avatarUrl)
        updateProfile(updatedProfile)
    }
}