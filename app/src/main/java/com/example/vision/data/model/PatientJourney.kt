package com.example.vision.data.model

import java.time.LocalDate

data class PatientJourney(
    val id: String,
    val patientId: String,
    val startDate: LocalDate,
    val milestones: List<JourneyMilestone>,
    val currentPhase: JourneyPhase,
    val nextMilestone: JourneyMilestone? = null
)

data class JourneyMilestone(
    val id: String,
    val title: String,
    val description: String,
    val phase: JourneyPhase,
    val date: LocalDate,
    val isCompleted: Boolean = false,
    val icon: String = "",
    val notes: String = ""
)

enum class JourneyPhase {
    CONSULTATION,
    IMPRESSION_TAKING,
    FITTING,
    ADJUSTMENT,
    ADAPTATION,
    MAINTENANCE,
    REPLACEMENT
}

data class ReplacementSchedule(
    val prostheticId: String,
    val installDate: LocalDate,
    val recommendedReplacementDate: LocalDate,
    val lastCheckupDate: LocalDate? = null,
    val nextCheckupDate: LocalDate,
    val wearTime: Int, // in months
    val condition: ProstheticCondition = ProstheticCondition.GOOD
)

enum class ProstheticCondition {
    EXCELLENT,
    GOOD,
    FAIR,
    NEEDS_ATTENTION,
    REPLACE_SOON
}