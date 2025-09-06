package com.example.vision.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vision.ui.theme.ThemeType
import com.example.vision.ui.theme.barlowBody16

data class ThemeDetails(
    val name: String,
    val description: String,
    val icon: ImageVector,
    val colors: List<Color>
)

fun getThemeDetails(theme: ThemeType): ThemeDetails {
    return when (theme) {
        ThemeType.CALM_SERENITY -> ThemeDetails(
            name = "Calm & Serenity",
            description = "Soft blues for a calming effect",
            icon = Icons.Default.Water,
            colors = listOf(
                Color(0xFF87CEEB),
                Color(0xFFB0E0E6),
                Color(0xFFCCCCFF)
            )
        )

        ThemeType.NATURE_HARMONY -> ThemeDetails(
            name = "Nature & Harmony",
            description = "Gentle greens for natural comfort",
            icon = Icons.Default.Park,
            colors = listOf(
                Color(0xFF9CAF88),
                Color(0xFF98FF98),
                Color(0xFF8B8C64)
            )
        )

        ThemeType.WARM_COMFORT -> ThemeDetails(
            name = "Warm Comfort",
            description = "Cozy neutrals for warmth",
            icon = Icons.Default.WbSunny,
            colors = listOf(
                Color(0xFFFFF8DC),
                Color(0xFFF5F5DC),
                Color(0xFF8B7D6B)
            )
        )

        ThemeType.COOL_NEUTRAL -> ThemeDetails(
            name = "Cool Neutral",
            description = "Balanced grays for focus",
            icon = Icons.Default.AcUnit,
            colors = listOf(
                Color(0xFFD3D3D3),
                Color(0xFF8D8478),
                Color(0xFFB8B8B8)
            )
        )

        ThemeType.LIGHT_SENSITIVE -> ThemeDetails(
            name = "Light Sensitive",
            description = "Rose tints to reduce glare",
            icon = Icons.Default.RemoveRedEye,
            colors = listOf(
                Color(0xFFFDEFF2), // very soft pink (background)
                Color(0xFFEFDDE2), // muted rose tint (cards/surfaces)
                Color(0xFF444444)  // dark gray for text (high contrast, not pure black)
            )
        )
    }
}

@Composable
fun ThemeOptionItem(
    theme: ThemeType,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val themeDetails = getThemeDetails(theme)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    themeDetails.icon,
                    contentDescription = themeDetails.name,
                    tint = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    modifier = Modifier.size(28.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = themeDetails.name,
                        style = Typography.barlowBody16,
                        fontWeight = if (isSelected) {
                            FontWeight.SemiBold
                        } else {
                            FontWeight.Normal
                        }
                    )
                    Text(
                        text = themeDetails.description,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    // Color preview
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        themeDetails.colors.forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(color)
                            )
                        }
                    }
                }
            }

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}