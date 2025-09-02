package com.example.vision.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Calm & Serenity Theme - Soft blues
private val CalmSerenityLightColors = lightColorScheme(
    primary = SkyBlue,
    secondary = PowderBlue,
    tertiary = Periwinkle,
    background = NeutralBackground,
    surface = NeutralSurface,
    onPrimary = SoftTextPrimary,
    onSecondary = SoftTextPrimary,
    onBackground = SoftTextPrimary,
    onSurface = SoftTextPrimary,
    onSurfaceVariant = SoftTextSecondary
)

private val CalmSerenityDarkColors = darkColorScheme(
    primary = SkyBlueDark,
    secondary = PowderBlueDark,
    tertiary = PeriwinkleDark,
    background = NeutralBackgroundDark,
    surface = NeutralSurfaceDark,
    onPrimary = SoftTextPrimaryDark,
    onSecondary = SoftTextPrimaryDark,
    onBackground = SoftTextPrimaryDark,
    onSurface = SoftTextPrimaryDark,
    onSurfaceVariant = SoftTextSecondaryDark
)

// Nature & Harmony Theme - Gentle greens
private val NatureHarmonyLightColors = lightColorScheme(
    primary = SageGreen,
    secondary = MintGreen,
    tertiary = OliveGreen,
    background = NeutralBackground,
    surface = NeutralSurface,
    onPrimary = SoftTextPrimary,
    onSecondary = SoftTextPrimary,
    onBackground = SoftTextPrimary,
    onSurface = SoftTextPrimary,
    onSurfaceVariant = SoftTextSecondary
)

private val NatureHarmonyDarkColors = darkColorScheme(
    primary = SageGreenDark,
    secondary = MintGreenDark,
    tertiary = OliveGreenDark,
    background = NeutralBackgroundDark,
    surface = NeutralSurfaceDark,
    onPrimary = SoftTextPrimaryDark,
    onSecondary = SoftTextPrimaryDark,
    onBackground = SoftTextPrimaryDark,
    onSurface = SoftTextPrimaryDark,
    onSurfaceVariant = SoftTextSecondaryDark
)

// Warm Comfort Theme - Warm neutrals
private val WarmComfortLightColors = lightColorScheme(
    primary = Taupe,
    secondary = Beige,
    tertiary = Cream,
    background = Cream,
    surface = Beige,
    onPrimary = SoftTextPrimaryDark,
    onSecondary = SoftTextPrimary,
    onBackground = SoftTextPrimary,
    onSurface = SoftTextPrimary,
    onSurfaceVariant = SoftTextSecondary
)

private val WarmComfortDarkColors = darkColorScheme(
    primary = TaupeDark,
    secondary = BeigeDark,
    tertiary = CreamDark,
    background = NeutralBackgroundDark,
    surface = NeutralSurfaceDark,
    onPrimary = SoftTextPrimaryDark,
    onSecondary = SoftTextPrimaryDark,
    onBackground = SoftTextPrimaryDark,
    onSurface = SoftTextPrimaryDark,
    onSurfaceVariant = SoftTextSecondaryDark
)

// Cool Neutral Theme - Balance and sophistication
private val CoolNeutralLightColors = lightColorScheme(
    primary = Stone,
    secondary = LightGray,
    tertiary = SoftGray,
    background = NeutralBackground,
    surface = NeutralSurface,
    onPrimary = SoftTextPrimaryDark,
    onSecondary = SoftTextPrimary,
    onBackground = SoftTextPrimary,
    onSurface = SoftTextPrimary,
    onSurfaceVariant = SoftTextSecondary
)

private val CoolNeutralDarkColors = darkColorScheme(
    primary = StoneDark,
    secondary = LightGrayDark,
    tertiary = SoftGrayDark,
    background = NeutralBackgroundDark,
    surface = NeutralSurfaceDark,
    onPrimary = SoftTextPrimaryDark,
    onSecondary = SoftTextPrimaryDark,
    onBackground = SoftTextPrimaryDark,
    onSurface = SoftTextPrimaryDark,
    onSurfaceVariant = SoftTextSecondaryDark
)

// Light Sensitive Theme - Rose/pink tints for reduced glare
private val LightSensitiveLightColors = lightColorScheme(
    primary = SoftPink,
    secondary = RoseTint,
    tertiary = LightGray,
    background = RoseTint,
    surface = NeutralSurface,
    onPrimary = SoftTextPrimary,
    onSecondary = SoftTextPrimary,
    onBackground = SoftTextPrimary,
    onSurface = SoftTextPrimary,
    onSurfaceVariant = SoftTextSecondary
)

private val LightSensitiveDarkColors = darkColorScheme(
    primary = SoftPinkDark,
    secondary = RoseTintDark,
    tertiary = LightGrayDark,
    background = NeutralBackgroundDark,
    surface = NeutralSurfaceDark,
    onPrimary = SoftTextPrimaryDark,
    onSecondary = SoftTextPrimaryDark,
    onBackground = SoftTextPrimaryDark,
    onSurface = SoftTextPrimaryDark,
    onSurfaceVariant = SoftTextSecondaryDark
)

@Composable
fun VisionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeType: ThemeType = ThemeType.CALM_SERENITY,
    dynamicColor: Boolean = false, // Disabled by default for eye-friendly themes
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        else -> {
            when (themeType) {
                ThemeType.CALM_SERENITY -> {
                    if (darkTheme) {
                        CalmSerenityDarkColors
                    } else {
                        CalmSerenityLightColors
                    }
                }

                ThemeType.NATURE_HARMONY -> {
                    if (darkTheme) {
                        NatureHarmonyDarkColors
                    } else {
                        NatureHarmonyLightColors
                    }
                }

                ThemeType.WARM_COMFORT -> {
                    if (darkTheme) {
                        WarmComfortDarkColors
                    } else {
                        WarmComfortLightColors
                    }
                }

                ThemeType.COOL_NEUTRAL -> {
                    if (darkTheme) {
                        CoolNeutralDarkColors
                    } else {
                        CoolNeutralLightColors
                    }
                }

                ThemeType.LIGHT_SENSITIVE -> {
                    if (darkTheme) {
                        LightSensitiveDarkColors
                    } else {
                        LightSensitiveLightColors
                    }
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}