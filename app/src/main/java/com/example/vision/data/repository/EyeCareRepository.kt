package com.example.vision.data.repository

import com.example.vision.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EyeCareRepository @Inject constructor() {

    private val _appointments = MutableStateFlow(getDemoAppointments())
    val appointments: Flow<List<Appointment>> = _appointments.asStateFlow()

    private val _careInstructions = MutableStateFlow(getDemoCareInstructions())
    val careInstructions: Flow<List<CareInstruction>> = _careInstructions.asStateFlow()

    private val _patientJourney = MutableStateFlow(getDemoPatientJourney())
    val patientJourney: Flow<PatientJourney> = _patientJourney.asStateFlow()

    private val _eyeData = MutableStateFlow(getDemoEyeData())
    val eyeData: Flow<EyeData?> = _eyeData.asStateFlow()

    private val _replacementSchedule = MutableStateFlow(getDemoReplacementSchedule())
    val replacementSchedule: Flow<ReplacementSchedule?> = _replacementSchedule.asStateFlow()

    fun updateAppointmentStatus(appointmentId: String, status: AppointmentStatus) {
        _appointments.value = _appointments.value.map { appointment ->
            if (appointment.id == appointmentId) {
                appointment.copy(status = status)
            } else {
                appointment
            }
        }
    }

    fun markCareInstructionCompleted(instructionId: String) {
        _careInstructions.value = _careInstructions.value.map { instruction ->
            if (instruction.id == instructionId) {
                instruction.copy(
                    isCompleted = true,
                    lastCompletedDate = LocalDate.now().toString()
                )
            } else {
                instruction
            }
        }
    }

    fun updateJourneyMilestone(milestoneId: String, completed: Boolean) {
        val currentJourney = _patientJourney.value
        val updatedMilestones = currentJourney.milestones.map { milestone ->
            if (milestone.id == milestoneId) {
                milestone.copy(isCompleted = completed)
            } else {
                milestone
            }
        }
        _patientJourney.value = currentJourney.copy(milestones = updatedMilestones)
    }

    fun addEyePhoto(photo: EyePhoto) {
        _eyeData.value?.let { currentData ->
            _eyeData.value = currentData.copy(
                photos = currentData.photos + photo
            )
        }
    }

    private fun getDemoAppointments(): List<Appointment> = listOf(
        Appointment(
            id = "1",
            title = "Follow-up Checkup",
            type = AppointmentType.FOLLOW_UP,
            dateTime = LocalDateTime.now().plusDays(7).withHour(10).withMinute(30),
            location = "Eye Care Clinic, 123 Medical Center",
            doctorName = "Dr. Sarah Johnson",
            notes = "6-month routine checkup",
            status = AppointmentStatus.SCHEDULED
        ),
        Appointment(
            id = "2",
            title = "Prosthetic Adjustment",
            type = AppointmentType.ADJUSTMENT,
            dateTime = LocalDateTime.now().plusDays(14).withHour(14).withMinute(0),
            location = "Prosthetic Eye Center",
            doctorName = "Dr. Michael Chen",
            notes = "Minor adjustment needed for comfort",
            status = AppointmentStatus.SCHEDULED
        ),
        Appointment(
            id = "3",
            title = "Initial Fitting",
            type = AppointmentType.FITTING,
            dateTime = LocalDateTime.now().minusDays(30).withHour(9).withMinute(0),
            location = "Eye Care Clinic",
            doctorName = "Dr. Sarah Johnson",
            notes = "First prosthetic fitting completed successfully",
            status = AppointmentStatus.COMPLETED
        )
    )

    private fun getDemoCareInstructions(): List<CareInstruction> = listOf(
        CareInstruction(
            id = "1",
            title = "Morning Cleaning",
            description = "Gently clean the prosthetic eye with saline solution",
            category = CareCategory.CLEANING,
            frequency = CareFrequency.DAILY,
            timeOfDay = LocalTime.of(8, 0),
            isCompleted = false
        ),
        CareInstruction(
            id = "2",
            title = "Evening Removal",
            description = "Remove and store the prosthetic eye in clean solution",
            category = CareCategory.REMOVAL,
            frequency = CareFrequency.DAILY,
            timeOfDay = LocalTime.of(22, 0),
            isCompleted = false
        ),
        CareInstruction(
            id = "3",
            title = "Weekly Deep Clean",
            description = "Thorough cleaning with special solution",
            category = CareCategory.CLEANING,
            frequency = CareFrequency.WEEKLY,
            isCompleted = false
        ),
        CareInstruction(
            id = "4",
            title = "Hand Hygiene",
            description = "Always wash hands before handling",
            category = CareCategory.HYGIENE,
            frequency = CareFrequency.AS_NEEDED,
            isCompleted = true,
            lastCompletedDate = LocalDate.now().toString()
        )
    )

    private fun getDemoPatientJourney(): PatientJourney {
        val milestones = listOf(
            JourneyMilestone(
                id = "m1",
                title = "Initial Consultation",
                description = "First meeting with ocularist",
                phase = JourneyPhase.CONSULTATION,
                date = LocalDate.now().minusMonths(3),
                isCompleted = true
            ),
            JourneyMilestone(
                id = "m2",
                title = "Impression Taking",
                description = "Creating mold of eye socket",
                phase = JourneyPhase.IMPRESSION_TAKING,
                date = LocalDate.now().minusMonths(2).minusWeeks(2),
                isCompleted = true
            ),
            JourneyMilestone(
                id = "m3",
                title = "First Fitting",
                description = "Initial prosthetic fitting and adjustments",
                phase = JourneyPhase.FITTING,
                date = LocalDate.now().minusMonths(1),
                isCompleted = true
            ),
            JourneyMilestone(
                id = "m4",
                title = "Adaptation Period",
                description = "Getting comfortable with the prosthetic",
                phase = JourneyPhase.ADAPTATION,
                date = LocalDate.now(),
                isCompleted = false
            ),
            JourneyMilestone(
                id = "m5",
                title = "Regular Maintenance",
                description = "Ongoing care and checkups",
                phase = JourneyPhase.MAINTENANCE,
                date = LocalDate.now().plusMonths(1),
                isCompleted = false
            )
        )

        return PatientJourney(
            id = "journey1",
            patientId = "patient1",
            startDate = LocalDate.now().minusMonths(3),
            milestones = milestones,
            currentPhase = JourneyPhase.ADAPTATION,
            nextMilestone = milestones[3]
        )
    }

    private fun getDemoEyeData(): EyeData = EyeData(
        id = "eye1",
        patientId = "patient1",
        eyeSide = EyeSide.LEFT,
        irisColor = IrisColor(
            primaryColor = "Hazel",
            secondaryColor = "Green",
            pattern = IrisPattern.RADIAL,
            colorCode = "#8B7355"
        ),
        pupilSize = 3.5f,
        scleraShade = "Natural White",
        limbusDefinition = "Soft",
        specialCharacteristics = listOf("Gold flecks", "Dark limbal ring"),
        measurements = EyeMeasurements(
            horizontalDiameter = 24.0f,
            verticalDiameter = 23.5f,
            irisSize = 11.5f,
            depthMeasurement = 3.0f
        ),
        fittingDate = LocalDate.now().minusMonths(1),
        photos = emptyList()
    )

    private fun getDemoReplacementSchedule(): ReplacementSchedule = ReplacementSchedule(
        prostheticId = "prosthetic1",
        installDate = LocalDate.now().minusMonths(1),
        recommendedReplacementDate = LocalDate.now().plusYears(3),
        lastCheckupDate = LocalDate.now().minusWeeks(2),
        nextCheckupDate = LocalDate.now().plusMonths(6),
        wearTime = 1,
        condition = ProstheticCondition.EXCELLENT
    )
}