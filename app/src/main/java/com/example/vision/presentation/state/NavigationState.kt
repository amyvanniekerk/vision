package com.example.vision.presentation.state

data class NavigationState(
    val currentRoute: String = NavigationRoute.LOGIN,
    val previousRoute: String? = null,
    val isBottomBarVisible: Boolean = false
)

object NavigationRoute {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
    const val SPLASH = "splash"
    const val JOURNEY = "journey"
    const val COLOR_MATCH = "color_match"
}