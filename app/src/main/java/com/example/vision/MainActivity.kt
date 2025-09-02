package com.example.vision

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.vision.core.navigation.VisionNavigation
import com.example.vision.presentation.state.AuthEffect
import com.example.vision.presentation.state.NavigationRoute
import com.example.vision.data.preferences.ThemePreferences
import com.example.vision.presentation.viewmodel.AuthViewModel
import com.example.vision.ui.theme.ThemeType
import com.example.vision.ui.theme.VisionTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val themePreferences = ThemePreferences(context)
            
            val isDarkMode by produceState(initialValue = false) {
                themePreferences.isDarkMode.collect { value = it }
            }
            
            val themeType by produceState(initialValue = ThemeType.CALM_SERENITY) {
                themePreferences.themeType.collect { value = it }
            }
            
            VisionTheme(
                darkTheme = isDarkMode,
                themeType = themeType
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val authViewModel: AuthViewModel = hiltViewModel()
                    val authState by authViewModel.state.collectAsStateWithLifecycle()
                    
                    LaunchedEffect(authViewModel) {
                        authViewModel.effect.collectLatest { effect ->
                            when (effect) {
                                is AuthEffect.NavigateToHome -> {
                                    navController.navigate(NavigationRoute.HOME) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                }
                                is AuthEffect.NavigateToLogin -> {
                                    navController.navigate(NavigationRoute.LOGIN) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                }
                                else -> {}
                            }
                        }
                    }
                    
                    VisionNavigation(navController = navController)
                }
            }
        }
    }
}