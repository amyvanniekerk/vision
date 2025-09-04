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
    const val EMPLOYEE_HOME = "employee_home"
    const val ADMIN_HOME = "admin_home"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
    const val APPEARANCE = "appearance"
    const val CUSTOMER_LIST = "customer_list"
    const val CUSTOMER_DETAILS = "customer_details/{customerId}"
    const val MANAGE_PROFILE = "manage_profile"
    const val EMPLOYEE_MANAGEMENT = "employee_management"
    const val CREATE_EMPLOYEE = "create_employee"
    const val SPLASH = "splash"
    const val JOURNEY = "journey"
    const val COLOR_MATCH = "color_match"
    const val CARE_GUIDE = "care_guide"

    fun customerDetails(customerId: String) = "customer_details/$customerId"
}