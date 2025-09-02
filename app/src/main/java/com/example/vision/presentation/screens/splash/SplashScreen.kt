package com.example.vision.presentation.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToMain: () -> Unit
) {
    // Animation for the eye blinking
    val infiniteTransition = rememberInfiniteTransition(label = "eye_blink")
    
    // Eye openness animation (1f = fully open, 0f = closed)
    val eyeOpenness by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                1f at 0
                1f at 2400
                0f at 2500
                0f at 2600
                1f at 2700
                1f at 3000
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "eye_openness"
    )
    
    // Pupil size animation for realistic effect
    val pupilSize by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pupil_size"
    )
    
    LaunchedEffect(key1 = true) {
        delay(3500) // Show splash for 3.5 seconds
        onNavigateToMain()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Blinking Eye Logo
            Canvas(
                modifier = Modifier.size(200.dp)
            ) {
                drawBlinkingEye(
                    eyeOpenness = eyeOpenness,
                    pupilSize = pupilSize
                )
            }
            
            // App Name
            Text(
                text = "Vision",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            Text(
                text = "Eye Care Companion",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

private fun DrawScope.drawBlinkingEye(
    eyeOpenness: Float,
    pupilSize: Float
) {
    val eyeWidth = size.width * 0.8f
    val eyeHeight = size.height * 0.5f * eyeOpenness
    val centerX = size.width / 2
    val centerY = size.height / 2
    
    // Draw eye shape (almond shape that closes)
    val eyePath = Path().apply {
        if (eyeOpenness > 0.1f) {
            // Upper eyelid
            moveTo(centerX - eyeWidth / 2, centerY)
            quadraticBezierTo(
                centerX, centerY - eyeHeight,
                centerX + eyeWidth / 2, centerY
            )
            
            // Lower eyelid
            quadraticBezierTo(
                centerX, centerY + eyeHeight,
                centerX - eyeWidth / 2, centerY
            )
            close()
        } else {
            // Closed eye - just a line
            moveTo(centerX - eyeWidth / 2, centerY)
            lineTo(centerX + eyeWidth / 2, centerY)
        }
    }
    
    // Draw eye outline
    drawPath(
        path = eyePath,
        color = Color.White,
        style = Stroke(width = 4.dp.toPx())
    )
    
    // Draw iris and pupil only if eye is open
    if (eyeOpenness > 0.2f) {
        // Iris
        drawCircle(
            color = Color(0xFF4A90E2),
            radius = eyeHeight * 0.7f,
            center = Offset(centerX, centerY)
        )
        
        // Pupil
        drawCircle(
            color = Color.Black,
            radius = eyeHeight * pupilSize,
            center = Offset(centerX, centerY)
        )
        
        // Light reflection on pupil
        drawCircle(
            color = Color.White.copy(alpha = 0.8f),
            radius = eyeHeight * 0.1f,
            center = Offset(centerX - eyeHeight * 0.15f, centerY - eyeHeight * 0.15f)
        )
    }
    
    // Eyelashes (only on top when eye is open)
    if (eyeOpenness > 0.3f) {
        for (i in 0..5) {
            val angle = -30f + (i * 12f)
            val startX = centerX - eyeWidth / 2 + (i * eyeWidth / 5)
            val startY = centerY - eyeHeight * 0.9f
            
            rotate(angle, pivot = Offset(startX, startY)) {
                drawLine(
                    color = Color.White,
                    start = Offset(startX, startY),
                    end = Offset(startX, startY - 15.dp.toPx()),
                    strokeWidth = 2.dp.toPx()
                )
            }
        }
    }
}