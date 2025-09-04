package com.example.vision.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vision.data.model.Location
import kotlin.text.ifEmpty

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


                    ProfileInfoRow(
                        icon = Icons.Default.Person,
                        label = "Name",
                        value = "${profile?.firstName} ${profile?.lastName}"
                    )

                    if (!profile?.phoneNumber.isNullOrEmpty()) {
                        ProfileInfoRow(
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
                            ProfileInfoRow(
                                icon = Icons.Default.LocationOn,
                                label = "Location",
                                value = locationText
                            )
                        }
                    }

                    if (profile?.gender != null) {
                        ProfileInfoRow(
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

@Composable
fun EditProfile(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val profile = state.profile

    var firstName by remember(profile) { mutableStateOf(profile?.firstName ?: "") }
    var lastName by remember(profile) { mutableStateOf(profile?.lastName ?: "") }
    var bio by remember(profile) { mutableStateOf(profile?.bio ?: "") }
    var phoneNumber by remember(profile) { mutableStateOf(profile?.phoneNumber ?: "") }
    var city by remember(profile) { mutableStateOf(profile?.location?.city ?: "") }
    var country by remember(profile) { mutableStateOf(profile?.location?.country ?: "") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Bio") },
                minLines = 3,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = country,
                onValueChange = { country = it },
                label = { Text("Country") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedButton(
                onClick = { viewModel.handleEvent(ProfileEvent.CancelEditing) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    val updatedProfile = profile?.copy(
                        firstName = firstName,
                        lastName = lastName,
                        displayName = "$firstName $lastName",
                        bio = bio.ifEmpty { null },
                        phoneNumber = phoneNumber.ifEmpty { null },
                        location = if (city.isNotEmpty() || country.isNotEmpty()) {
                            profile.location?.copy(
                                city = city.ifEmpty { null },
                                country = country.ifEmpty { null }
                            ) ?: Location(
                                city = city.ifEmpty { null },
                                country = country.ifEmpty { null }
                            )
                        } else null
                    )
                    viewModel.handleEvent(ProfileEvent.UpdateProfile(updatedProfile))
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
        }
    }
}