package com.example.vision.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.vision.presentation.state.NavigationRoute
import com.example.vision.presentation.screens.onboarding.AuthViewModel
import com.example.vision.presentation.screens.customer.details.CustomerViewModel
import com.example.vision.presentation.screens.customer.details.CustomerEvent
import com.example.vision.data.model.UserRole
import com.example.vision.presentation.screens.customer.careGuide.CareGuideScreen
import com.example.vision.presentation.screens.customer.color.ColorMatchScreen
import com.example.vision.presentation.screens.customer.details.CustomerDetailsScreen
import com.example.vision.presentation.screens.customer.details.CustomerListScreen
import com.example.vision.presentation.screens.customer.journey.JourneyScreen
import com.example.vision.presentation.screens.employee.CreateEmployeeScreen
import com.example.vision.presentation.screens.employee.EmployeeHomeScreen
import com.example.vision.presentation.screens.employee.EmployeeManagementScreen
import com.example.vision.presentation.screens.home.AdminHomeScreen
import com.example.vision.presentation.screens.home.HomeScreen
import com.example.vision.presentation.screens.onboarding.LoginScreen
import com.example.vision.presentation.screens.onboarding.RegisterScreen
import com.example.vision.presentation.screens.profile.ProfileScreen
import com.example.vision.presentation.screens.settings.AppearanceScreen
import com.example.vision.presentation.screens.settings.SettingsScreen

@Composable
fun VisionNavigation(
    navController: NavHostController
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val customerViewModel: CustomerViewModel = hiltViewModel()
    val authState by authViewModel.state.collectAsStateWithLifecycle()
    val customerState by customerViewModel.state.collectAsStateWithLifecycle()
    
    val startDestination = when {
        !authState.isAuthenticated -> NavigationRoute.LOGIN
        authState.user?.role == UserRole.ADMIN -> NavigationRoute.ADMIN_HOME
        authState.user?.role == UserRole.EMPLOYEE -> NavigationRoute.EMPLOYEE_HOME
        else -> NavigationRoute.HOME
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
                },
                onNavigateToCareGuide = {
                    navController.navigate(NavigationRoute.CARE_GUIDE) {
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(NavigationRoute.EMPLOYEE_HOME) {
            EmployeeHomeScreen(
                customers = customerState.customers,
                onNavigateToProfile = {
                    navController.navigate(NavigationRoute.PROFILE) {
                        launchSingleTop = true
                    }
                },
                onNavigateToCustomerList = {
                    navController.navigate(NavigationRoute.CUSTOMER_LIST) {
                        launchSingleTop = true
                    }
                },
                onNavigateToCustomerDetails = { customer ->
                    navController.navigate(NavigationRoute.customerDetails(customer.id)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(NavigationRoute.ADMIN_HOME) {
            AdminHomeScreen(
                customers = customerState.customers.filter { it.role == UserRole.CUSTOMER },
                employees = customerState.customers.filter { it.role == UserRole.EMPLOYEE },
                onNavigateToProfile = {
                    navController.navigate(NavigationRoute.PROFILE) {
                        launchSingleTop = true
                    }
                },
                onNavigateToEmployeeManagement = {
                    navController.navigate(NavigationRoute.EMPLOYEE_MANAGEMENT) {
                        launchSingleTop = true
                    }
                },
                onNavigateToCustomerList = {
                    navController.navigate(NavigationRoute.CUSTOMER_LIST) {
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(NavigationRoute.EMPLOYEE_MANAGEMENT) {
            EmployeeManagementScreen(
                employees = customerState.customers.filter { it.role == UserRole.EMPLOYEE },
                onNavigateBack = {
                    navController.popBackStack()
                },
                onCreateEmployee = {
                    navController.navigate(NavigationRoute.CREATE_EMPLOYEE) {
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(NavigationRoute.CREATE_EMPLOYEE) {
            CreateEmployeeScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onCreateEmployee = { employee ->
                    customerViewModel.handleEvent(CustomerEvent.AddEmployee(employee))
                    navController.popBackStack()
                }
            )
        }
        
        composable(NavigationRoute.CUSTOMER_LIST) {
            CustomerListScreen(
                customers = customerState.customers,
                onCustomerClick = { customer ->
                    navController.navigate(NavigationRoute.customerDetails(customer.id)) {
                        launchSingleTop = true
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = NavigationRoute.CUSTOMER_DETAILS,
            arguments = listOf(
                navArgument("customerId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId") ?: ""
            val customer = customerState.customers.find { it.id == customerId }
            
            if (customer != null) {
                CustomerDetailsScreen(
                    customer = customer,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            } else {
                // Customer not found, navigate back
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            }
        }
        
        composable(NavigationRoute.PROFILE) {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToSettings = {
                    navController.navigate(NavigationRoute.SETTINGS) {
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(NavigationRoute.SETTINGS) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToAppearance = {
                    navController.navigate(NavigationRoute.APPEARANCE) {
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(NavigationRoute.APPEARANCE) {
            AppearanceScreen(
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
        
        composable(NavigationRoute.CARE_GUIDE) {
            CareGuideScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}