package com.example.vision.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vision.data.model.*
import com.example.vision.data.repository.EyeCareRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ColorMatchViewModel @Inject constructor(
    private val repository: EyeCareRepository
) : ViewModel() {
    
    private val _colorMatchState = MutableStateFlow(ColorMatchState())
    val colorMatchState: StateFlow<ColorMatchState> = _colorMatchState.asStateFlow()
    
    val eyeData = repository.eyeData
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    
    fun capturePhoto(uri: String, type: PhotoType) {
        viewModelScope.launch {
            val photo = EyePhoto(
                id = System.currentTimeMillis().toString(),
                uri = uri,
                type = type,
                dateTaken = LocalDate.now(),
                notes = ""
            )
            repository.addEyePhoto(photo)
            
            // Simulate color analysis
            if (type == PhotoType.NATURAL_EYE) {
                analyzeColors(uri)
            }
        }
    }
    
    private fun analyzeColors(photoUri: String) {
        _colorMatchState.update { 
            it.copy(
                isAnalyzing = true,
                currentPhotoUri = photoUri
            )
        }
        
        // Simulate color analysis delay
        viewModelScope.launch {
            kotlinx.coroutines.delay(2000)
            
            val result = ColorMatchResult(
                matchPercentage = 92.5f,
                suggestedColors = listOf(
                    "Hazel Base #8B7355",
                    "Green Accent #4A7C59",
                    "Brown Shadow #654321",
                    "Gold Fleck #FFD700"
                ),
                notes = "Excellent match found. Radial pattern detected with gold flecks."
            )
            
            _colorMatchState.update {
                it.copy(
                    isAnalyzing = false,
                    matchResult = result,
                    matchHistory = it.matchHistory + result
                )
            }
        }
    }
    
    fun clearAnalysis() {
        _colorMatchState.update {
            it.copy(
                currentPhotoUri = null,
                matchResult = null
            )
        }
    }
}

data class ColorMatchState(
    val isAnalyzing: Boolean = false,
    val currentPhotoUri: String? = null,
    val matchResult: ColorMatchResult? = null,
    val matchHistory: List<ColorMatchResult> = emptyList()
)