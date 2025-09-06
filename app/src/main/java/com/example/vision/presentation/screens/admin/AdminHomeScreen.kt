package com.example.vision.presentation.screens.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vision.data.model.User
import com.example.vision.presentation.screens.admin.components.AdminActionCard
import com.example.vision.presentation.screens.admin.components.AdminWelcomeCard
import com.example.vision.presentation.screens.admin.components.SystemOverviewCard
import com.example.vision.presentation.screens.onboarding.AuthEffect
import com.example.vision.presentation.screens.onboarding.AuthEvent
import com.example.vision.presentation.screens.onboarding.AuthViewModel
import com.example.vision.ui.theme.barlowBody12
import com.example.vision.ui.theme.barlowBold20
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    customers: List<User> = emptyList(),
    employees: List<User> = emptyList(),
    onNavigateToProfile: () -> Unit,
    onNavigateToEmployeeManagement: () -> Unit,
    onNavigateToCustomerList: () -> Unit,
    onNavigateToLogin: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState by authViewModel.state.collectAsStateWithLifecycle()
    val user = authState.user

    LaunchedEffect(authViewModel) {
        authViewModel.effect.collectLatest { effect ->
            when (effect) {
                is AuthEffect.NavigateToLogin -> onNavigateToLogin()
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "EyeCare Admin",
                            style = Typography.barlowBold20,
                        )
                        Text(
                            text = "System Administration",
                            style = Typography.barlowBody12,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {
                        authViewModel.handleEvent(AuthEvent.Logout)
                    }) {
                        Icon(
                            Icons.Default.PowerSettingsNew,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Welcome Section
            item {
                AdminWelcomeCard(
                    adminName = user?.profile?.displayName ?: ""
                )
            }

            // Quick Actions for Admins
            item {
                Text(
                    text = "System Management",
                    style = Typography.barlowBold20,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AdminActionCard(
                        title = "Employees",
                        icon = Icons.Default.Groups,
                        description = "Manage staff",
                        count = employees.size,
                        gradient = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                            )
                        ),
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToEmployeeManagement
                    )

                    AdminActionCard(
                        title = "Customers",
                        icon = Icons.Default.People,
                        description = "View all customers",
                        count = customers.size,
                        gradient = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.secondaryContainer,
                                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f)
                            )
                        ),
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToCustomerList
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AdminActionCard(
                        title = "Analytics",
                        icon = Icons.Default.Analytics,
                        description = "System reports",
                        count = null,
                        gradient = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.tertiary,
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f)
                            )
                        ),
                        modifier = Modifier.weight(1f),
                        onClick = { /* TODO: Navigate to analytics */ }
                    )

                    AdminActionCard(
                        title = "Settings",
                        icon = Icons.Default.Settings,
                        description = "System config",
                        count = null,
                        gradient = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.outline,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.8f)
                            )
                        ),
                        modifier = Modifier.weight(1f),
                        onClick = { /* TODO: Navigate to admin settings */ }
                    )
                }
            }

            // System Overview
            item {
                SystemOverviewCard(
                    totalCustomers = customers.size,
                    totalEmployees = employees.size,
                    activeUsers = customers.count { it.isActive } + employees.count { it.isActive }
                )
            }
        }
    }
}