package com.example.vision.presentation.screens.customer.journey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vision.data.model.*
import com.example.vision.presentation.screens.customer.journey.JourneyViewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyScreen(
    onNavigateBack: () -> Unit,
    viewModel: JourneyViewModel = hiltViewModel()
) {
    val appointments by viewModel.upcomingAppointments.collectAsStateWithLifecycle(emptyList())
    val journey by viewModel.patientJourney.collectAsStateWithLifecycle()
    val careInstructions by viewModel.dailyCareInstructions.collectAsStateWithLifecycle(emptyList())
    val replacementSchedule by viewModel.replacementSchedule.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Journey") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Progress Overview Card
            item {
                ProgressCard(journey)
            }
            
            // Next Appointment Card
            item {
                appointments.firstOrNull()?.let { appointment ->
                    NextAppointmentCard(appointment)
                }
            }
            
            // Daily Care Checklist
            item {
                Text(
                    "Daily Care Routine",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            items(careInstructions) { instruction ->
                CareInstructionCard(
                    instruction = instruction,
                    onComplete = { viewModel.markCareInstructionCompleted(instruction.id) }
                )
            }
            
            // Journey Milestones
            item {
                Text(
                    "Journey Milestones",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            items(journey.milestones) { milestone ->
                MilestoneCard(
                    milestone = milestone,
                    onToggle = { viewModel.updateMilestone(milestone.id, !milestone.isCompleted) }
                )
            }
            
            // Replacement Schedule
            replacementSchedule?.let { schedule ->
                item {
                    ReplacementScheduleCard(schedule)
                }
            }
        }
    }
}

@Composable
fun ProgressCard(journey: PatientJourney) {
    val completedCount = journey.milestones.count { it.isCompleted }
    val totalCount = journey.milestones.size
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Your Progress",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Current Phase: ${journey.currentPhase.name.replace("_", " ")}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    "${(progress * 100).toInt()}%",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "$completedCount of $totalCount milestones completed",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun NextAppointmentCard(appointment: Appointment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    appointment.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    appointment.dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    appointment.location,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun CareInstructionCard(
    instruction: CareInstruction,
    onComplete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onComplete
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = instruction.isCompleted,
                onCheckedChange = { onComplete() }
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    instruction.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    instruction.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                instruction.timeOfDay?.let { time ->
                    Text(
                        "Scheduled: ${time.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Icon(
                when (instruction.category) {
                    CareCategory.CLEANING -> Icons.Default.CleaningServices
                    CareCategory.INSERTION -> Icons.Default.AddCircle
                    CareCategory.REMOVAL -> Icons.Default.RemoveCircle
                    CareCategory.STORAGE -> Icons.Default.Inventory
                    CareCategory.HYGIENE -> Icons.Default.Wash
                    CareCategory.EMERGENCY -> Icons.Default.Warning
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun MilestoneCard(
    milestone: JourneyMilestone,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (milestone.isCompleted) 
                MaterialTheme.colorScheme.tertiaryContainer 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (milestone.isCompleted)
                            MaterialTheme.colorScheme.tertiary
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (milestone.isCompleted) Icons.Default.Check else Icons.Default.Schedule,
                    contentDescription = null,
                    tint = if (milestone.isCompleted) 
                        MaterialTheme.colorScheme.onTertiary 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    milestone.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    milestone.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    milestone.date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ReplacementScheduleCard(schedule: ReplacementSchedule) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Prosthetic Status",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                AssistChip(
                    onClick = {},
                    label = { Text(schedule.condition.name.replace("_", " ")) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = when (schedule.condition) {
                            ProstheticCondition.EXCELLENT -> Color(0xFF4CAF50)
                            ProstheticCondition.GOOD -> Color(0xFF8BC34A)
                            ProstheticCondition.FAIR -> Color(0xFFFFC107)
                            ProstheticCondition.NEEDS_ATTENTION -> Color(0xFFFF9800)
                            ProstheticCondition.REPLACE_SOON -> Color(0xFFF44336)
                        }.copy(alpha = 0.2f)
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Next Checkup:", fontSize = 14.sp)
                Text(
                    schedule.nextCheckupDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Replacement Due:", fontSize = 14.sp)
                Text(
                    schedule.recommendedReplacementDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}