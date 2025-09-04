package com.example.vision.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vision.R
import com.example.vision.ui.modifiers.dpToSp

val openSansFont = FontFamily(
    Font(R.font.opensans_regular, FontWeight.Normal),
    Font(R.font.opensans_medium, FontWeight.Medium),
    Font(R.font.opensans_semibold, FontWeight.SemiBold),
    Font(R.font.opensans_bold, FontWeight.Bold)
)

val barlowFont = FontFamily(
    Font(R.font.barlow_regular, FontWeight.Normal),
    Font(R.font.barlow_medium, FontWeight.Medium),
    Font(R.font.barlow_semibold, FontWeight.SemiBold),
    Font(R.font.barlow_bold, FontWeight.Bold),
)

val cachetStdFamily = FontFamily(
    Font(R.font.cachet_std_book, FontWeight.Normal)
)


// open sans fonts

val Typography.openSansBody14: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = openSansFont,
            fontSize = dpToSp(dp = 14.dp)
        )
    }

val Typography.openSansBody16: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = openSansFont,
            fontSize = dpToSp(dp = 16.dp)
        )
    }

val Typography.openSansBody18: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = openSansFont,
            fontSize = dpToSp(dp = 18.dp)
        )
    }

val Typography.openSansBody20: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = openSansFont,
            fontSize = dpToSp(dp = 20.dp)
        )
    }

val Typography.openSansBold24: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = openSansFont,
            fontSize = dpToSp(dp = 24.dp),
            fontWeight = FontWeight.Bold,
            lineHeight = dpToSp(dp = 24.dp),
            letterSpacing = dpToSp(dp = (-0.2f).dp),
        )
    }

val Typography.openSansBold32: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = openSansFont,
            fontSize = dpToSp(dp = 32.dp),
            fontWeight = FontWeight.Bold
        )
    }


// barlow fonts

val Typography.barlowBody14: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = barlowFont,
            fontSize = dpToSp(dp = 14.dp)
        )
    }

val Typography.barlowBold14: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = barlowFont,
            fontSize = dpToSp(dp = 14.dp),
            fontWeight = FontWeight.Bold
        )
    }

val Typography.barlowBody16: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = barlowFont,
            fontSize = dpToSp(dp = 16.dp)
        )
    }

val Typography.barlowBody18: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = barlowFont,
            fontSize = dpToSp(dp = 18.dp)
        )
    }

val Typography.barlowBody20: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = barlowFont,
            fontSize = dpToSp(dp = 20.dp)
        )
    }

val Typography.barlowBold24: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = barlowFont,
            fontSize = dpToSp(dp = 24.dp),
            fontWeight = FontWeight.Bold,
            lineHeight = dpToSp(dp = 24.dp),
            letterSpacing = dpToSp(dp = (-0.2f).dp),
        )
    }

val Typography.barlowBold32: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = barlowFont,
            fontSize = dpToSp(dp = 32.dp),
            fontWeight = FontWeight.Bold
        )
    }
