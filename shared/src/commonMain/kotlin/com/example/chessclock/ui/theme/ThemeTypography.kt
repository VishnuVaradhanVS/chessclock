package com.example.chessclock.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
data class CustomTypography(
    val matrix: TextStyle,
    val lcd: TextStyle,
    val digital: TextStyle
)

// Global reference hook that throws a clear warning if initialization fails
val LocalCustomTypography = staticCompositionLocalOf<CustomTypography> {
    error("CustomTypography was not provided! Make sure your layout is wrapped inside ChessClockTheme.")
}