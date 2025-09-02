package com.example.vision.data.model

import java.time.LocalTime

data class CareInstruction(
    val id: String,
    val title: String,
    val description: String,
    val category: CareCategory,
    val frequency: CareFrequency,
    val timeOfDay: LocalTime? = null,
    val iconResource: String = "",
    val videoUrl: String? = null,
    val isCompleted: Boolean = false,
    val lastCompletedDate: String? = null
)

enum class CareCategory {
    CLEANING,
    INSERTION,
    REMOVAL,
    STORAGE,
    HYGIENE,
    EMERGENCY
}

enum class CareFrequency {
    DAILY,
    TWICE_DAILY,
    WEEKLY,
    MONTHLY,
    AS_NEEDED
}

data class CareRoutine(
    val morning: List<CareInstruction>,
    val evening: List<CareInstruction>,
    val weekly: List<CareInstruction>,
    val monthly: List<CareInstruction>
)