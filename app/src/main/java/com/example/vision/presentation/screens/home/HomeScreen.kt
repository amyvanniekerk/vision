package com.example.vision.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vision.R
import com.example.vision.data.model.Appointment
import com.example.vision.data.model.CareInstruction
import com.example.vision.data.model.PatientJourney
import com.example.vision.data.model.ProstheticCondition
import com.example.vision.data.model.ReplacementSchedule
import com.example.vision.presentation.screens.customer.journey.JourneyViewModel
import com.example.vision.presentation.screens.onboarding.AuthEffect
import com.example.vision.presentation.screens.onboarding.AuthEvent
import com.example.vision.presentation.screens.onboarding.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToJourney: () -> Unit,
    onNavigateToColorMatch: () -> Unit,
    onNavigateToCareGuide: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel(),
    journeyViewModel: JourneyViewModel = hiltViewModel()
) {
    val authState by authViewModel.state.collectAsStateWithLifecycle()
    val user = authState.user
    val appointments by journeyViewModel.upcomingAppointments.collectAsStateWithLifecycle(emptyList())
    val dailyCare by journeyViewModel.dailyCareInstructions.collectAsStateWithLifecycle(emptyList())
    val journey by journeyViewModel.patientJourney.collectAsStateWithLifecycle()
    val replacementSchedule by journeyViewModel.replacementSchedule.collectAsStateWithLifecycle()

    LaunchedEffect(authViewModel) {
        authViewModel.effect.collectLatest { effect ->
            when (effect) {
                is AuthEffect.NavigateToLogin -> onNavigateToLogin()
                else -> {}
            }
        }
    }

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
                        Icon(Icons.Default.AccountCircle, contentDescription = null)
                    }
                    IconButton(onClick = {
                        authViewModel.handleEvent(AuthEvent.Logout)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
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
                ProstheticCondition.EXCELLENT -> colorResource(id = R.color.success)
                ProstheticCondition.GOOD -> colorResource(id = R.color.success)
                ProstheticCondition.FAIR -> colorResource(id = R.color.warning)
                ProstheticCondition.NEEDS_ATTENTION -> colorResource(id = R.color.warning)
                ProstheticCondition.REPLACE_SOON -> colorResource(id = R.color.error)
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
                        ProstheticCondition.EXCELLENT -> colorResource(id = R.color.success)
                        ProstheticCondition.GOOD -> colorResource(id = R.color.success)
                        ProstheticCondition.FAIR -> colorResource(id = R.color.warning)
                        ProstheticCondition.NEEDS_ATTENTION -> colorResource(id = R.color.warning)
                        ProstheticCondition.REPLACE_SOON -> colorResource(id = R.color.error)
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
                    ProstheticCondition.EXCELLENT -> colorResource(id = R.color.success)
                    ProstheticCondition.GOOD -> colorResource(id = R.color.success)
                    ProstheticCondition.FAIR -> colorResource(id = R.color.warning)
                    ProstheticCondition.NEEDS_ATTENTION -> colorResource(id = R.color.warning)
                    ProstheticCondition.REPLACE_SOON -> colorResource(id = R.color.error)
                }
            )
        }
    }
}