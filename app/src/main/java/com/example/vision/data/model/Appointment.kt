package com.example.vision.data.model

import java.time.LocalDateTime

data class Appointment(
    val id: String,
    val title: String,
    val type: AppointmentType,
    val dateTime: LocalDateTime,
    val location: String,
    val doctorName: String,
    val notes: String = "",
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED,
    val reminderEnabled: Boolean = true
)

enum class AppointmentType {
    INITIAL_CONSULTATION,
    FITTING,
    ADJUSTMENT,
    FOLLOW_UP,
    REPLACEMENT,
    EMERGENCY
}

enum class AppointmentStatus {
    SCHEDULED,
    COMPLETED,
    CANCELLED,
    RESCHEDULED
}