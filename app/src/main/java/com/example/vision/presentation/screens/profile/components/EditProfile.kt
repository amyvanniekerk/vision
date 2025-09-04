package com.example.vision.presentation.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vision.data.model.Location
import com.example.vision.presentation.screens.profile.ProfileEvent
import com.example.vision.presentation.screens.profile.ProfileViewModel
import com.example.vision.ui.components.StandardButton
import com.example.vision.ui.components.StandardInputField
import com.example.vision.ui.modifiers.clearFocusOnTap

@Composable
fun EditProfile(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val profile = state.profile

    var firstName by remember(profile) { mutableStateOf(profile?.firstName ?: "") }
    var lastName by remember(profile) { mutableStateOf(profile?.lastName ?: "") }

    var phoneNumber by remember(profile) { mutableStateOf(profile?.phoneNumber ?: "") }

    var idOrPassportNumber by remember(profile) {
        mutableStateOf(
            profile?.idOrPassportNumber ?: ""
        )
    }
    var email by remember(profile) { mutableStateOf(profile?.email ?: "") }

    var city by remember(profile) { mutableStateOf(profile?.location?.city ?: "") }
    var country by remember(profile) { mutableStateOf(profile?.location?.country ?: "") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clearFocusOnTap()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StandardInputField(
                    label = "First Name",
                    value = firstName,
                    onValueChange = { firstName = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )
                StandardInputField(
                    label = "Last Name",
                    value = lastName,
                    onValueChange = { lastName = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )
                StandardInputField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )
                StandardInputField(
                    label = "Phone Number",
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )
                StandardInputField(
                    label = "Id or Passport Number",
                    value = idOrPassportNumber,
                    onValueChange = { idOrPassportNumber = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )
                StandardInputField(
                    label = "City",
                    value = city,
                    onValueChange = { city = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )
                StandardInputField(
                    label = "Country",
                    value = country,
                    onValueChange = { country = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )
            }
        }
        // Fixed buttons at bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(Modifier.weight(1f))
            StandardButton(
                text = "Cancel",
                modifier = Modifier
                    .requiredHeight(48.dp)
                    .weight(1f),
                onClick = {
                    viewModel.handleEvent(ProfileEvent.CancelEditing)
                }
            )

            StandardButton(
                text = "Save",
                modifier = Modifier
                    .requiredHeight(48.dp)
                    .weight(1f),
                onClick = {
                    val updatedProfile = profile?.copy(
                        firstName = firstName,
                        lastName = lastName,
                        displayName = "$firstName $lastName",
                        email = email,
                        idOrPassportNumber = idOrPassportNumber,
                        phoneNumber = if (idOrPassportNumber.isEmpty()) null else idOrPassportNumber,
                        location = if (city.isNotEmpty() || country.isNotEmpty()) {
                            profile.location?.copy(
                                city = if (city.isEmpty()) null else city,
                                country = if (country.isEmpty()) null else country
                            ) ?: Location(
                                city = if (city.isEmpty()) null else city,
                                country = if (country.isEmpty()) null else country
                            )
                        } else null
                    )
                    viewModel.handleEvent(ProfileEvent.UpdateProfile(updatedProfile))
                }
            )
        }
    }
}