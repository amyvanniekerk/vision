package com.example.vision.ui.theme

enum class ThemeType {
    CALM_SERENITY,    // Soft blues for calm and trust
    NATURE_HARMONY,   // Gentle greens for nature feel
    WARM_COMFORT,     // Warm neutrals for comfort
    COOL_NEUTRAL,     // Cool neutrals for balance
    LIGHT_SENSITIVE   // Rose/pink tints for light sensitivity
}

//   Usage:
//
//  To use a specific theme in your app, simply pass the themeType parameter:
//
//  VisionTheme(
//      themeType = ThemeType.NATURE_HARMONY, // or any other theme
//      darkTheme = isSystemInDarkTheme()
//  ) {
//      // Your app content
//  }