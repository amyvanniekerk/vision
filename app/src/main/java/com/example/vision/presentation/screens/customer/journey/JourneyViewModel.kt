package com.example.vision.presentation.screens.customer.journey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vision.data.model.AppointmentStatus
import com.example.vision.data.model.CareFrequency
import com.example.vision.data.model.JourneyPhase
import com.example.vision.data.model.PatientJourney
import com.example.vision.data.repository.EyeCareRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class JourneyViewModel @Inject constructor(
    private val repository: EyeCareRepository
) : ViewModel() {

    val appointments = repository.appointments
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val careInstructions = repository.careInstructions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val patientJourney = repository.patientJourney
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = PatientJourney(
                id = "",
                patientId = "",
                startDate = LocalDate.now(),
                milestones = emptyList(),
                currentPhase = JourneyPhase.CONSULTATION
            )
        )

    val replacementSchedule = repository.replacementSchedule
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = null
        )

    val upcomingAppointments = appointments.map { list ->
        list.filter { it.status == AppointmentStatus.SCHEDULED }
            .sortedBy { it.dateTime }
    }

    val dailyCareInstructions = careInstructions.map { list ->
        list.filter {
            it.frequency == CareFrequency.DAILY ||
            it.frequency == CareFrequency.TWICE_DAILY
        }
    }

    val completedMilestones = patientJourney.map { journey ->
        journey.milestones.count { it.isCompleted }
    }

    fun updateAppointmentStatus(appointmentId: String, status: AppointmentStatus) {
        viewModelScope.launch {
            repository.updateAppointmentStatus(appointmentId, status)
        }
    }

    fun markCareInstructionCompleted(instructionId: String) {
        viewModelScope.launch {
            repository.markCareInstructionCompleted(instructionId)
        }
    }

    fun updateMilestone(milestoneId: String, completed: Boolean) {
        viewModelScope.launch {
            repository.updateJourneyMilestone(milestoneId, completed)
        }
    }
}