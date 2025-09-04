package com.example.vision.presentation.screens.customer.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vision.data.model.EyeSide
import com.example.vision.data.model.User
import com.example.vision.presentation.screens.customer.details.components.ContactInfoCard
import com.example.vision.presentation.screens.customer.details.components.CustomerProfileHeader
import com.example.vision.presentation.screens.customer.details.components.EyeDataCard
import com.example.vision.presentation.screens.customer.details.components.MedicalHistoryCard

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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = onEditCustomer) {
                        Icon(Icons.Default.Edit, contentDescription = null)
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
            customer.profile?.eyeData?.forEach { eyeData ->
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