package com.example.vision.presentation.screens.customer.color

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vision.data.model.PhotoType
import com.example.vision.presentation.screens.customer.color.components.AnalysisResultCard
import com.example.vision.presentation.screens.customer.color.components.CameraCaptureCard
import com.example.vision.presentation.screens.customer.color.components.ColorComparisonCard
import com.example.vision.presentation.screens.customer.color.components.ColorSuggestionCard
import com.example.vision.presentation.screens.customer.color.components.CurrentEyeDataCard
import com.example.vision.presentation.screens.customer.color.components.TipsCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorMatchScreen(
    onNavigateBack: () -> Unit,
    viewModel: ColorMatchViewModel = hiltViewModel()
) {
    val colorMatchState by viewModel.colorMatchState.collectAsStateWithLifecycle()
    val eyeData by viewModel.eyeData.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Color Matching") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
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
            // Current Eye Data Card
            eyeData?.let { data ->
                item {
                    CurrentEyeDataCard(data)
                }
            }

            // Camera Capture Section
            item {
                CameraCaptureCard(
                    isAnalyzing = colorMatchState.isAnalyzing,
                    onCapture = {
                        // Simulate photo capture
                        viewModel.capturePhoto("demo_photo_uri", PhotoType.NATURAL_EYE)
                    }
                )
            }

            // Analysis Results
            colorMatchState.matchResult?.let { result ->
                item {
                    AnalysisResultCard(result)
                }

                item {
                    Text(
                        "Suggested Colors",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(result.suggestedColors) { color ->
                            ColorSuggestionCard(color)
                        }
                    }
                }
            }

            // Color Comparison Tool
            item {
                ColorComparisonCard()
            }

            // Tips Section
            item {
                TipsCard()
            }
        }
    }
}