package com.example.vision.presentation.screens.settings

import com.example.vision.ui.theme.ThemeType

data class SettingsState(
    val isDarkMode: Boolean = false,
    val themeType: ThemeType = ThemeType.CALM_SERENITY
)

sealed class SettingsEvent {
    data class ToggleDarkMode(val isDarkMode: Boolean) : SettingsEvent()
    data class ChangeThemeType(val themeType: ThemeType) : SettingsEvent()
}

sealed class SettingsEffect {
    data class ShowMessage(val message: String) : SettingsEffect()
}