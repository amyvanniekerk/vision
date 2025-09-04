package com.example.vision.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vision.presentation.screens.profile.components.EditProfile
import com.example.vision.presentation.screens.profile.components.ProfileCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProfile(
    onNavigateBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val profile = state.profile

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage your profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    if (!state.isEditing) {
                        IconButton(onClick = { viewModel.handleEvent(ProfileEvent.StartEditing) }) {
                            Icon(Icons.Default.Edit, contentDescription = null)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (state.isEditing) {
                    EditProfile()
                } else {
                    ProfileCard(
                        icon = Icons.Default.Person,
                        label = "Name",
                        value = "${profile?.firstName} ${profile?.lastName}"
                    )

                    if (!profile?.phoneNumber.isNullOrEmpty()) {
                        ProfileCard(
                            icon = Icons.Default.Phone,
                            label = "Phone",
                            value = profile.phoneNumber
                        )
                    }

                    if (profile?.location != null) {
                        val locationText = listOfNotNull(
                            profile.location.city,
                            profile.location.country
                        ).joinToString(", ")

                        if (locationText.isNotEmpty()) {
                            ProfileCard(
                                icon = Icons.Default.LocationOn,
                                label = "Location",
                                value = locationText
                            )
                        }
                    }

                    if (profile?.gender != null) {
                        ProfileCard(
                            icon = Icons.Default.Person,
                            label = "Gender",
                            value = profile.gender.name.lowercase()
                                .replaceFirstChar { it.uppercase() }.replace("_", " ")
                        )
                    }
                }
            }
        }
    }
}