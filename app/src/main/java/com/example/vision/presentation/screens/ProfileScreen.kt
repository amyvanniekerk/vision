package com.example.vision.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vision.data.model.Gender
import com.example.vision.data.model.UserProfile
import com.example.vision.presentation.state.ProfileEvent
import com.example.vision.presentation.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
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
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (!state.isEditing) {
                        IconButton(onClick = { viewModel.handleEvent(ProfileEvent.StartEditing) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (profile != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile Picture",
                            modifier = Modifier.size(60.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    
                    if (!state.isEditing) {
                        Text(
                            text = profile.displayName,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        if (!profile.bio.isNullOrEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Text(
                                    text = profile.bio,
                                    modifier = Modifier.padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ProfileInfoRow(
                                    icon = Icons.Default.Person,
                                    label = "Name",
                                    value = "${profile.firstName} ${profile.lastName}"
                                )
                                
                                if (!profile.phoneNumber.isNullOrEmpty()) {
                                    ProfileInfoRow(
                                        icon = Icons.Default.Phone,
                                        label = "Phone",
                                        value = profile.phoneNumber
                                    )
                                }
                                
                                if (profile.location != null) {
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
                                
                                if (profile.gender != null) {
                                    ProfileInfoRow(
                                        icon = Icons.Default.Person,
                                        label = "Gender",
                                        value = profile.gender.name.replace("_", " ")
                                    )
                                }
                            }
                        }
                    } else {
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
                                    val updatedProfile = profile.copy(
                                        firstName = firstName,
                                        lastName = lastName,
                                        displayName = "$firstName $lastName",
                                        bio = bio.ifEmpty { null },
                                        phoneNumber = phoneNumber.ifEmpty { null },
                                        location = if (city.isNotEmpty() || country.isNotEmpty()) {
                                            profile.location?.copy(
                                                city = city.ifEmpty { null },
                                                country = country.ifEmpty { null }
                                            ) ?: com.example.vision.data.model.Location(
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
            }
            
            if (state.error != null) {
                Snackbar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    action = {
                        TextButton(onClick = { viewModel.handleEvent(ProfileEvent.ClearError) }) {
                            Text("Dismiss")
                        }
                    }
                ) {
                    Text(state.error ?: "")
                }
            }
        }
    }
}

@Composable
private fun ProfileInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 16.sp
            )
        }
    }
}