package com.example.vision.presentation.screens.customer.careGuide

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Soap
import androidx.compose.material.icons.filled.Texture
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vision.presentation.screens.customer.careGuide.components.CareStep
import com.example.vision.presentation.screens.customer.careGuide.components.ExpandableCareStepsCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CareGuideScreen(
    onNavigateBack: () -> Unit
) {
    val dailyCleaningSteps = listOf(
        CareStep(
            "Wash your hands",
            "with soap and water to prevent introducing germs.",
            Icons.Default.CleaningServices
        ),
        CareStep(
            "Remove the prosthesis",
            "by looking up and pressing on the lower eyelid.",
            Icons.Default.RemoveRedEye
        ),
        CareStep(
            "Rinse the prosthesis",
            "with clear, lukewarm water to remove any loose debris.",
            Icons.Default.WaterDrop
        ),
        CareStep(
            "Clean the prosthesis",
            "by rubbing it with a few drops of mild, unscented soap or a sterile saline solution.",
            Icons.Default.CleaningServices
        ),
        CareStep(
            "Rinse thoroughly",
            "with warm water to ensure all soap or solution is removed.",
            Icons.Default.WaterDrop
        ),
        CareStep(
            "Air dry",
            "the prosthesis or gently pat it with a clean, lint-free material.",
            Icons.Default.Air
        ),
        CareStep(
            "Store the dry prosthesis",
            "in a closed, dry container when not in your eye.",
            Icons.Default.Inventory
        )
    )

    val monthlyCleaningSteps = listOf(
        CareStep(
            "Wash Hands",
            "Always wash and rinse your hands thoroughly before handling your prosthesis.",
            Icons.Default.CleaningServices
        ),
        CareStep(
            "Remove Prosthesis",
            "Once a month, or as advised by your ocularist, carefully remove the prosthetic eye.",
            Icons.Default.RemoveRedEye
        ),
        CareStep(
            "Clean Prosthesis",
            "Gently scrub the prosthetic eye with your fingertips using mild, unscented soap and warm water. Avoid rubbing with cloths, as this can cause dullness.",
            Icons.Default.Soap
        ),
        CareStep(
            "Rinse Thoroughly",
            "Rinse the prosthesis under a stream of water to ensure all soap residue is removed.",
            Icons.Default.WaterDrop
        ),
        CareStep(
            "Dry and Lubricate",
            "Lightly dry the prosthesis with a soft tissue and then apply a few drops of an approved lubricating solution or specialized artificial eye lubricant before re-inserting it.",
            Icons.Default.Opacity
        ),
        CareStep(
            "Clean Socket",
            "While the prosthesis is out, use a specialized saline eyewash to gently irrigate your eye socket, ensuring it's clean and comfortable.",
            Icons.Default.LocalHospital
        )
    )

    var isDailyStepsExpanded by remember { mutableStateOf(false) }
    var isMonthlyStepsExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Care Guide") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            // Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                           Icons.Default.HealthAndSafety,
                            contentDescription = "Care Guide",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Eye Care Routine",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Follow these steps to maintain your prosthetic eye",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Daily Cleaning Steps - Expandable
            item {
                ExpandableCareStepsCard(
                    title = "Daily Cleaning Steps",
                    subtitle = "Follow these steps every day",
                    steps = dailyCleaningSteps,
                    isExpanded = isDailyStepsExpanded,
                    onToggleExpanded = { isDailyStepsExpanded = !isDailyStepsExpanded }
                )
            }

            // Monthly Cleaning & Care - Expandable
            item {
                ExpandableCareStepsCard(
                    title = "Monthly Cleaning & Care",
                    subtitle = "Deep cleaning every 1-4 weeks",
                    steps = monthlyCleaningSteps,
                    isExpanded = isMonthlyStepsExpanded,
                    onToggleExpanded = { isMonthlyStepsExpanded = !isMonthlyStepsExpanded }
                )
            }

            // What to Avoid
            item {
                Text(
                    text = "What to Avoid",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.8f)
                    )
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
                                Icons.Default.Warning,
                                contentDescription = "Warning",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "What to Avoid",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        InfoItem(
                            title = "Harsh chemicals:",
                            description = "Never use detergents, alcohol, household cleaners, toothpaste, or bleaches.",
                            icon = Icons.Default.Science,
                            tintColor = MaterialTheme.colorScheme.error
                        )

                        InfoItem(
                            title = "Hot water:",
                            description = "Extreme temperature fluctuations can damage the material.",
                            icon = Icons.Default.Thermostat,
                            tintColor = MaterialTheme.colorScheme.error
                        )

                        InfoItem(
                            title = "Abrasive materials:",
                            description = "Avoid using rough cloths or brushes, as they can wear away the polished surface.",
                            icon = Icons.Default.Texture,
                            tintColor = MaterialTheme.colorScheme.error
                        )

                        InfoItem(
                            title = "Soaking in water:",
                            description = "Do not store the prosthesis in water for extended periods when it's not in use.",
                            icon = Icons.Default.Pool,
                            tintColor = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            // Important Notes
            item {
                Text(
                    text = "Important Notes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)
                    )
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
                                Icons.Default.Info,
                                contentDescription = "Important",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Professional Care",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        InfoItem(
                            title = "Regular professional care:",
                            description = "Schedule annual or bi-annual visits with your ocularist for professional polishing and maintenance.",
                            icon = Icons.Default.Schedule,
                            tintColor = MaterialTheme.colorScheme.secondary
                        )

                        InfoItem(
                            title = "Lubricating drops:",
                            description = "Your ocularist may recommend lubricating eye drops for comfort.",
                            icon = Icons.Default.Opacity,
                            tintColor = MaterialTheme.colorScheme.secondary
                        )

                        InfoItem(
                            title = "Consult your ocularist:",
                            description = "Always check with your ocularist about recommended cleaning solutions and any specific products that may affect the material of your prosthesis.",
                            icon = Icons.AutoMirrored.Filled.ContactSupport,
                            tintColor = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            // Emergency Contact
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "Contact",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = "Need Help?",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Text(
                            text = "If you experience any issues or have questions about your prosthetic eye care, contact your ocularist immediately.",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoItem(
    title: String,
    description: String,
    icon: ImageVector,
    tintColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = tintColor
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = tintColor
            )
            Text(
                text = description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
            )
        }
    }
}