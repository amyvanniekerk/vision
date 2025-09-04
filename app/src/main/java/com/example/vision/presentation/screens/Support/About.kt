package com.example.vision.presentation.screens.Support

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vision.presentation.screens.Support.components.AboutCardRow
import com.example.vision.ui.theme.barlowBody14
import com.example.vision.ui.theme.barlowBody16
import com.example.vision.ui.theme.barlowBold32

@Composable
fun AboutBottomSheetContent() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Icon(
            imageVector = Icons.Default.Visibility,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Vision App",
            style = Typography.barlowBold32,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = "Version 1.0.0",
            style = Typography.barlowBody16,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = "Build 1.1 (2025 Sept 1)",
            style = Typography.barlowBody14,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )

        Spacer(Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AboutCardRow(
                    header = "Developer",
                    subText = "Vision Prosthetics Teams"
                )
                AboutCardRow(
                    header = "License",
                    subText = "Drivers",
                )
                AboutCardRow(
                    header = "Website",
                    subText = "www.catalystsoftware.co.za",
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "© 2025 Vision App. All rights reserved.",
            style = Typography.barlowBody14,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}