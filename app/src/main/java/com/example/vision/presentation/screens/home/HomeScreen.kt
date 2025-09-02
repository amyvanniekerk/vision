package com.example.vision.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vision.data.model.*
import com.example.vision.presentation.state.AuthEvent
import com.example.vision.presentation.viewmodel.AuthViewModel
import com.example.vision.presentation.viewmodel.JourneyViewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToJourney: () -> Unit,
    onNavigateToColorMatch: () -> Unit,
    onNavigateToCareGuide: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel(),
    journeyViewModel: JourneyViewModel = hiltViewModel()
) {
    val authState by authViewModel.state.collectAsStateWithLifecycle()
    val user = authState.user
    val appointments by journeyViewModel.upcomingAppointments.collectAsStateWithLifecycle(emptyList())
    val dailyCare by journeyViewModel.dailyCareInstructions.collectAsStateWithLifecycle(emptyList())
    val journey by journeyViewModel.patientJourney.collectAsStateWithLifecycle()
    val replacementSchedule by journeyViewModel.replacementSchedule.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text(
                            "EyeCare Pro",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Your Prosthetic Eye Companion",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                    }
                    IconButton(onClick = { 
                        authViewModel.handleEvent(AuthEvent.Logout) 
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
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
            // Welcome Section
            item {
                WelcomeCard(userName = user?.profile?.displayName ?: "Patient")
            }
            
            // Quick Actions
            item {
                Text(
                    "Quick Actions",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        QuickActionCard(
                            title = "Journey Tracker",
                            icon = Icons.Default.Timeline,
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                            onClick = onNavigateToJourney
                        )
                    }
                    item {
                        QuickActionCard(
                            title = "Color Match",
                            icon = Icons.Default.Palette,
                            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                            onClick = onNavigateToColorMatch
                        )
                    }
                    item {
                        QuickActionCard(
                            title = "Care Guide",
                            icon = Icons.Default.HealthAndSafety,
                            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                            onClick = onNavigateToCareGuide
                        )
                    }
                }
            }
            
            // Today's Care Reminder
            if (dailyCare.isNotEmpty()) {
                item {
                    Text(
                        "Today's Care",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                item {
                    DailyCareCard(
                        instructions = dailyCare.take(3),
                        onMarkComplete = { id ->
                            journeyViewModel.markCareInstructionCompleted(id)
                        }
                    )
                }
            }
            
            // Upcoming Appointment
            appointments.firstOrNull()?.let { appointment ->
                item {
                    Text(
                        "Next Appointment",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                item {
                    AppointmentCard(appointment)
                }
            }
            
            // Journey Progress
            item {
                JourneyProgressCard(
                    journey = journey,
                    onClick = onNavigateToJourney
                )
            }
            
            // Prosthetic Status
            replacementSchedule?.let { schedule ->
                item {
                    ProstheticStatusCard(schedule)
                }
            }
        }
    }
}

@Composable
fun WelcomeCard(userName: String) {
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.RemoveRedEye,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    "Welcome back, $userName!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    "Your prosthetic care companion",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(120.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun DailyCareCard(
    instructions: List<CareInstruction>,
    onMarkComplete: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            instructions.forEach { instruction ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = instruction.isCompleted,
                        onCheckedChange = { onMarkComplete(instruction.id) },
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            instruction.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        instruction.timeOfDay?.let { time ->
                            Text(
                                "at ${time.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppointmentCard(appointment: Appointment) {
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
            verticalAlignment = Alignment.CenterVertically
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
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    appointment.title,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    appointment.dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                )
                Text(
                    "with ${appointment.doctorName}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                )
            }
            
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun JourneyProgressCard(
    journey: PatientJourney,
    onClick: () -> Unit
) {
    val completedCount = journey.milestones.count { it.isCompleted }
    val totalCount = journey.milestones.size
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Your Journey",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${(progress * 100).toInt()}%",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "Current: ${journey.currentPhase.name.replace("_", " ")}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ProstheticStatusCard(schedule: ReplacementSchedule) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (schedule.condition) {
                ProstheticCondition.EXCELLENT -> Color(0xFF4CAF50)
                ProstheticCondition.GOOD -> Color(0xFF8BC34A)
                ProstheticCondition.FAIR -> Color(0xFFFFC107)
                ProstheticCondition.NEEDS_ATTENTION -> Color(0xFFFF9800)
                ProstheticCondition.REPLACE_SOON -> Color(0xFFF44336)
            }.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "Prosthetic Condition",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    schedule.condition.name.replace("_", " "),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (schedule.condition) {
                        ProstheticCondition.EXCELLENT -> Color(0xFF4CAF50)
                        ProstheticCondition.GOOD -> Color(0xFF8BC34A)
                        ProstheticCondition.FAIR -> Color(0xFFFFC107)
                        ProstheticCondition.NEEDS_ATTENTION -> Color(0xFFFF9800)
                        ProstheticCondition.REPLACE_SOON -> Color(0xFFF44336)
                    }
                )
            }
            
            Icon(
                when (schedule.condition) {
                    ProstheticCondition.EXCELLENT -> Icons.Default.CheckCircle
                    ProstheticCondition.GOOD -> Icons.Default.ThumbUp
                    ProstheticCondition.FAIR -> Icons.Default.Info
                    ProstheticCondition.NEEDS_ATTENTION -> Icons.Default.Warning
                    ProstheticCondition.REPLACE_SOON -> Icons.Default.Error
                },
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = when (schedule.condition) {
                    ProstheticCondition.EXCELLENT -> Color(0xFF4CAF50)
                    ProstheticCondition.GOOD -> Color(0xFF8BC34A)
                    ProstheticCondition.FAIR -> Color(0xFFFFC107)
                    ProstheticCondition.NEEDS_ATTENTION -> Color(0xFFFF9800)
                    ProstheticCondition.REPLACE_SOON -> Color(0xFFF44336)
                }
            )
        }
    }
}