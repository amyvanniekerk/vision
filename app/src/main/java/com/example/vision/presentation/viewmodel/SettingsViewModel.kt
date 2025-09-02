package com.example.vision.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.vision.core.base.BaseViewModel
import com.example.vision.data.preferences.ThemePreferences
import com.example.vision.presentation.state.SettingsEffect
import com.example.vision.presentation.state.SettingsEvent
import com.example.vision.presentation.state.SettingsState
import com.example.vision.ui.theme.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) : BaseViewModel<SettingsState, SettingsEvent, SettingsEffect>(SettingsState()) {
    
    init {
        observeThemePreferences()
    }
    
    override suspend fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ToggleDarkMode -> toggleDarkMode(event.isDarkMode)
            is SettingsEvent.ChangeThemeType -> changeThemeType(event.themeType)
        }
    }
    
    private fun observeThemePreferences() {
        themePreferences.isDarkMode
            .onEach { isDarkMode ->
                updateState { copy(isDarkMode = isDarkMode) }
            }
            .launchIn(viewModelScope)
            
        themePreferences.themeType
            .onEach { themeType ->
                updateState { copy(themeType = themeType) }
            }
            .launchIn(viewModelScope)
    }
    
    private suspend fun toggleDarkMode(isDarkMode: Boolean) {
        themePreferences.setDarkMode(isDarkMode)
        sendEffect(SettingsEffect.ShowMessage(
            if (isDarkMode) "Dark mode enabled" else "Light mode enabled"
        ))
    }
    
    private suspend fun changeThemeType(themeType: ThemeType) {
        themePreferences.setThemeType(themeType)
        sendEffect(SettingsEffect.ShowMessage("Theme changed successfully"))
    }
}