package com.example.vision.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.vision.ui.theme.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_preferences")

@Singleton
class ThemePreferences @Inject constructor(
    private val context: Context
) {
    private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    private val THEME_TYPE = stringPreferencesKey("theme_type")

    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_DARK_MODE] ?: false
        }

    val themeType: Flow<ThemeType> = context.dataStore.data
        .map { preferences ->
            try {
                ThemeType.valueOf(preferences[THEME_TYPE] ?: ThemeType.CALM_SERENITY.name)
            } catch (e: IllegalArgumentException) {
                ThemeType.CALM_SERENITY
            }
        }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDarkMode
        }
    }

    suspend fun setThemeType(themeType: ThemeType) {
        context.dataStore.edit { preferences ->
            preferences[THEME_TYPE] = themeType.name
        }
    }
}