package com.example.vision.presentation.screens.Support.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vision.ui.theme.barlowBody14
import com.example.vision.ui.theme.barlowSemiBold14

@Composable
fun AboutCardRow(
    header: String,
    subText: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = header,
            style = Typography.barlowBody14,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = subText,
            style = Typography.barlowSemiBold14,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}