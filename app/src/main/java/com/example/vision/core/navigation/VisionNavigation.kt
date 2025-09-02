package com.example.vision.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vision.presentation.screens.*
import com.example.vision.presentation.state.NavigationRoute
import com.example.vision.presentation.viewmodel.AuthViewModel

@Composable
fun VisionNavigation(
    navController: NavHostController
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.state.collectAsStateWithLifecycle()
    
    val startDestination = if (authState.isAuthenticated) {
        NavigationRoute.HOME
    } else {
        NavigationRoute.LOGIN
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationRoute.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(NavigationRoute.REGISTER) {
                        launchSingleTop = true
                    }
                },
                onNavigateToHome = {
                    navController.navigate(NavigationRoute.HOME) {
                        popUpTo(NavigationRoute.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(NavigationRoute.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(NavigationRoute.LOGIN) {
                        popUpTo(NavigationRoute.REGISTER) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToHome = {
                    navController.navigate(NavigationRoute.HOME) {
                        popUpTo(NavigationRoute.REGISTER) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(NavigationRoute.HOME) {
            HomeScreen(
                onNavigateToProfile = {
                    navController.navigate(NavigationRoute.PROFILE) {
                        launchSingleTop = true
                    }
                },
                onNavigateToJourney = {
                    navController.navigate(NavigationRoute.JOURNEY) {
                        launchSingleTop = true
                    }
                },
                onNavigateToColorMatch = {
                    navController.navigate(NavigationRoute.COLOR_MATCH) {
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(NavigationRoute.PROFILE) {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(NavigationRoute.JOURNEY) {
            JourneyScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(NavigationRoute.COLOR_MATCH) {
            ColorMatchScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}