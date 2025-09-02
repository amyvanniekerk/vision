package com.example.vision.presentation.screens.customer.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vision.data.model.User
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ContactInfoCard(customer: User) {
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
fun MedicalHistoryCard(customer: User) {
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
                    value = gender.name.lowercase().replaceFirstChar { it.uppercase() }
                        .replace("_", " ")
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
fun InfoRow(
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