package com.example.vision.presentation.screens.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vision.data.model.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetailsScreen(
    customer: User,
    onNavigateBack: () -> Unit,
    onAddPhoto: (EyeSide) -> Unit = {},
    onEditCustomer: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Customer Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEditCustomer) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Customer")
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
            // Customer Profile Header
            item {
                CustomerProfileHeader(customer = customer)
            }
            
            // Eye Data Summary
            customer.profile.eyeData.forEach { eyeData ->
                item {
                    EyeDataCard(
                        eyeData = eyeData,
                        onAddPhoto = { onAddPhoto(eyeData.eyeSide) }
                    )
                }
            }
            
            // Contact Information
            item {
                ContactInfoCard(customer = customer)
            }
            
            // Medical History Section
            item {
                MedicalHistoryCard(customer = customer)
            }
        }
    }
}

@Composable
private fun CustomerProfileHeader(customer: User) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }
            
            // Customer Info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = customer.profile.displayName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Customer ID: ${customer.id.take(8)}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                Text(
                    text = customer.email,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
                customer.profile.phoneNumber?.let { phone ->
                    Text(
                        text = phone,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
            
            // Status Badge
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (customer.isActive) 
                    MaterialTheme.colorScheme.secondary 
                else 
                    MaterialTheme.colorScheme.errorContainer
            ) {
                Text(
                    text = if (customer.isActive) "Active" else "Inactive",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (customer.isActive) 
                        MaterialTheme.colorScheme.onSecondary
                    else 
                        MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Composable
private fun EyeDataCard(
    eyeData: EyeData,
    onAddPhoto: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Eye Side Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.RemoveRedEye,
                        contentDescription = "Eye",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${eyeData.eyeSide.name.lowercase().replaceFirstChar { it.uppercase() }} Eye",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                IconButton(onClick = onAddPhoto) {
                    Icon(
                        Icons.Default.AddAPhoto,
                        contentDescription = "Add Photo",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            // Eye Measurements
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MeasurementItem(
                    label = "H. Diameter",
                    value = "${eyeData.measurements.horizontalDiameter}mm",
                    modifier = Modifier.weight(1f)
                )
                MeasurementItem(
                    label = "V. Diameter", 
                    value = "${eyeData.measurements.verticalDiameter}mm",
                    modifier = Modifier.weight(1f)
                )
                MeasurementItem(
                    label = "Iris Size",
                    value = "${eyeData.measurements.irisSize}mm",
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Iris Color Information
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Iris Details",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Primary Color",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = eyeData.irisColor.primaryColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        
                        eyeData.irisColor.secondaryColor?.let { secondary ->
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Secondary Color",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = secondary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Pattern",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = eyeData.irisColor.pattern.name.lowercase().replaceFirstChar { it.uppercase() },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            
            // Photos Section
            if (eyeData.photos.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Photos (${eyeData.photos.size})",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(eyeData.photos) { photo ->
                            EyePhotoItem(photo = photo)
                        }
                    }
                }
            }
            
            // Dates Information
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                eyeData.fittingDate?.let { date ->
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Fitting Date",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                eyeData.replacementDate?.let { date ->
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Next Replacement",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MeasurementItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EyePhotoItem(photo: EyePhoto) {
    Card(
        modifier = Modifier.size(120.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Visibility,
                    contentDescription = "Eye Photo",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                // Photo Type Badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp),
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                ) {
                    Text(
                        text = when (photo.type) {
                            PhotoType.NATURAL_EYE -> "Natural"
                            PhotoType.PROSTHETIC_EYE -> "Prosthetic"
                            PhotoType.COLOR_MATCH -> "Match"
                            PhotoType.FITTING_PROGRESS -> "Progress"
                            PhotoType.COMPARISON -> "Compare"
                        },
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 10.sp,
                        color = Color.White
                    )
                }
            }
            
            Text(
                text = photo.dateTaken.format(DateTimeFormatter.ofPattern("MMM dd")),
                modifier = Modifier.padding(8.dp),
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ContactInfoCard(customer: User) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ContactPhone,
                    contentDescription = "Contact",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Contact Information",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            customer.profile.phoneNumber?.let { phone ->
                InfoRow(
                    icon = Icons.Default.Phone,
                    label = "Phone",
                    value = phone
                )
            }
            
            InfoRow(
                icon = Icons.Default.Email,
                label = "Email",
                value = customer.email
            )
            
            customer.profile.location?.let { location ->
                val locationText = listOfNotNull(
                    location.city,
                    location.state,
                    location.country
                ).joinToString(", ")
                
                if (locationText.isNotEmpty()) {
                    InfoRow(
                        icon = Icons.Default.LocationOn,
                        label = "Location",
                        value = locationText
                    )
                }
            }
        }
    }
}

@Composable
private fun MedicalHistoryCard(customer: User) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.MedicalServices,
                    contentDescription = "Medical History",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Medical Information",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            customer.profile.dateOfBirth?.let { dob ->
                InfoRow(
                    icon = Icons.Default.Cake,
                    label = "Date of Birth",
                    value = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(dob)
                )
            }
            
            customer.profile.gender?.let { gender ->
                InfoRow(
                    icon = Icons.Default.Person,
                    label = "Gender",
                    value = gender.name.lowercase().replaceFirstChar { it.uppercase() }.replace("_", " ")
                )
            }
            
            // Eye Data Count
            if (customer.profile.eyeData.isNotEmpty()) {
                InfoRow(
                    icon = Icons.Default.RemoveRedEye,
                    label = "Eye Records",
                    value = "${customer.profile.eyeData.size} eye(s) on file"
                )
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
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
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}