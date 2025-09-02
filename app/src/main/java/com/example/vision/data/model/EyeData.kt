package com.example.vision.data.model

import java.time.LocalDate

data class EyeData(
    val id: String,
    val patientId: String,
    val eyeSide: EyeSide,
    val irisColor: IrisColor,
    val pupilSize: Float,
    val scleraShade: String,
    val limbusDefinition: String,
    val specialCharacteristics: List<String> = emptyList(),
    val measurements: EyeMeasurements,
    val fittingDate: LocalDate? = null,
    val replacementDate: LocalDate? = null,
    val photos: List<EyePhoto> = emptyList()
)

enum class EyeSide {
    LEFT,
    RIGHT
}

data class IrisColor(
    val primaryColor: String,
    val secondaryColor: String? = null,
    val pattern: IrisPattern,
    val colorCode: String = ""
)

enum class IrisPattern {
    SOLID,
    RADIAL,
    CRYPTS,
    FURROWS,
    MIXED
}

data class EyeMeasurements(
    val horizontalDiameter: Float,
    val verticalDiameter: Float,
    val irisSize: Float,
    val depthMeasurement: Float? = null
)

data class EyePhoto(
    val id: String,
    val uri: String,
    val type: PhotoType,
    val dateTaken: LocalDate,
    val notes: String = ""
)

enum class PhotoType {
    NATURAL_EYE,
    PROSTHETIC_EYE,
    COLOR_MATCH,
    FITTING_PROGRESS,
    COMPARISON
}

data class ColorMatchResult(
    val matchPercentage: Float,
    val suggestedColors: List<String>,
    val notes: String
)