package com.example.vision.presentation.screens.customer.color

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vision.data.model.ColorMatchResult
import com.example.vision.data.model.EyeData
import com.example.vision.data.model.PhotoType

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
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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

@Composable
fun CurrentEyeDataCard(eyeData: EyeData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "Current Prosthetic Data",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Eye Side", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(eyeData.eyeSide.name, fontWeight = FontWeight.Medium)
                }
                Column {
                    Text("Primary Color", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(eyeData.irisColor.primaryColor, fontWeight = FontWeight.Medium)
                }
                Column {
                    Text("Pattern", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(eyeData.irisColor.pattern.name, fontWeight = FontWeight.Medium)
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Color Preview
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF8B7355))
                        .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                )
                
                eyeData.irisColor.secondaryColor?.let {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4A7C59))
                            .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun CameraCaptureCard(
    isAnalyzing: Boolean,
    onCapture: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isAnalyzing) {
                    val infiniteTransition = rememberInfiniteTransition()
                    val rotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(2000, easing = LinearEasing)
                        )
                    )
                    
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .rotate(rotation),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        "Analyzing colors...",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.PhotoCamera,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Position natural eye in good lighting",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onCapture,
                enabled = !isAnalyzing,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isAnalyzing) "Analyzing..." else "Capture & Analyze")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedButton(
                onClick = { /* Open gallery */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Select from Gallery")
            }
        }
    }
}

@Composable
fun AnalysisResultCard(result: ColorMatchResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Match Analysis",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            when {
                                result.matchPercentage >= 90 -> Color(0xFF4CAF50)
                                result.matchPercentage >= 75 -> Color(0xFFFFC107)
                                else -> Color(0xFFFF9800)
                            }.copy(alpha = 0.2f)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        "${result.matchPercentage.toInt()}% Match",
                        fontWeight = FontWeight.Bold,
                        color = when {
                            result.matchPercentage >= 90 -> Color(0xFF4CAF50)
                            result.matchPercentage >= 75 -> Color(0xFFFFC107)
                            else -> Color(0xFFFF9800)
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LinearProgressIndicator(
                progress = result.matchPercentage / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = when {
                    result.matchPercentage >= 90 -> Color(0xFF4CAF50)
                    result.matchPercentage >= 75 -> Color(0xFFFFC107)
                    else -> Color(0xFFFF9800)
                }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                result.notes,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Composable
fun ColorSuggestionCard(colorString: String) {
    val parts = colorString.split("#")
    val colorName = parts[0].trim()
    val colorCode = if (parts.size > 1) "#${parts[1]}" else "#000000"
    
    Card(
        modifier = Modifier.width(120.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(
                        try {
                            Color(android.graphics.Color.parseColor(colorCode))
                        } catch (e: Exception) {
                            MaterialTheme.colorScheme.primary
                        }
                    )
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                colorName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            
            Text(
                colorCode,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ColorComparisonCard() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "Side-by-Side Comparison",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFF8B7355),
                                        Color(0xFF4A7C59),
                                        Color(0xFF8B7355)
                                    )
                                )
                            )
                            .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Natural Eye", fontSize = 12.sp)
                }
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFF8B7355),
                                        Color(0xFF4A7C59).copy(alpha = 0.8f),
                                        Color(0xFF8B7355)
                                    )
                                )
                            )
                            .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Prosthetic Match", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun TipsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Tips for Best Results",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val tips = listOf(
                "Use natural daylight for most accurate color capture",
                "Take multiple photos at different times of day",
                "Include both eyes in frame for comparison",
                "Avoid harsh shadows or direct flash"
            )
            
            tips.forEach { tip ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text("•", modifier = Modifier.padding(end = 8.dp))
                    Text(tip, fontSize = 14.sp)
                }
            }
        }
    }
}