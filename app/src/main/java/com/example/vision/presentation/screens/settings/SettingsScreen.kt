package com.example.vision.presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vision.presentation.screens.Support.AboutBottomSheetContent
import com.example.vision.presentation.screens.onboarding.AuthEffect
import com.example.vision.presentation.screens.onboarding.AuthEvent
import com.example.vision.presentation.screens.onboarding.AuthViewModel
import com.example.vision.presentation.screens.settings.components.SettingsItem
import com.example.vision.presentation.screens.settings.components.SettingsSection
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAppearance: () -> Unit,
    onNavigateToManage: () -> Unit,
    onNavigateToLogin: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    var showAboutSheet by remember { mutableStateOf(false) }

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
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Account Section
            SettingsSection(title = "Account") {
                SettingsItem(
                    icon = Icons.Default.Person,
                    title = "Profile",
                    subtitle = "Manage your personal information",
                    onClick = onNavigateToManage
                )
                SettingsItem(
                    icon = Icons.Default.Lock,
                    title = "Privacy",
                    subtitle = "Control your privacy settings",
                    onClick = { /* Navigate to privacy */ }
                )
                SettingsItem(
                    icon = Icons.Default.Security,
                    title = "Security",
                    subtitle = "Password and authentication",
                    onClick = { /* Navigate to security */ }
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Preferences Section
            SettingsSection(title = "Preferences") {
                SettingsItem(
                    icon = Icons.Default.Palette,
                    title = "Appearance",
                    subtitle = "Theme, colors, and display settings",
                    onClick = onNavigateToAppearance
                )
                SettingsItem(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    subtitle = "Manage notification preferences",
                    onClick = { /* Navigate to notifications */ }
                )
                SettingsItem(
                    icon = Icons.Default.Language,
                    title = "Language",
                    subtitle = "Choose your preferred language",
                    onClick = { /* Navigate to language */ }
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Support Section
            SettingsSection(title = "Support") {
                SettingsItem(
                    icon = Icons.Default.QuestionMark,
                    title = "Help Center",
                    subtitle = "Get help and find answers",
                    onClick = { /* Navigate to help */ }
                )
                SettingsItem(
                    icon = Icons.Default.Feedback,
                    title = "Feedback",
                    subtitle = "Share your thoughts with us",
                    onClick = { /* Navigate to feedback */ }
                )

                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "About",
                    subtitle = "App version and information",
                    onClick = {
                        showAboutSheet = true
                    }
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Actions Section
            SettingsSection(title = "Actions") {
                SettingsItem(
                    icon = Icons.Default.Cached,
                    title = "Clear Cache",
                    subtitle = "Free up storage space",
                    onClick = { /* Clear cache */ }
                )
                SettingsItem(
                    icon = Icons.Default.PowerSettingsNew,
                    title = "Sign Out",
                    subtitle = "Sign out of your account",
                    onClick = {
                        authViewModel.handleEvent(AuthEvent.Logout)
                    },
                    tint = MaterialTheme.colorScheme.error
                )
            }

            // App Version at bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Vision App",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Version 1.0.0",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }

    if (showAboutSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAboutSheet = false },
            sheetState = bottomSheetState
        ) {
            AboutBottomSheetContent()
        }
    }
}
