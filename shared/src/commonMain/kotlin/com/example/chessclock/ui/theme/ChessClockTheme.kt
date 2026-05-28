package com.example.chessclock.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import chessclock.shared.generated.resources.LCD14
import org.jetbrains.compose.resources.Font

// IMPORTANT: Replace this path with your project's specific generated namespace package string
import chessclock.shared.generated.resources.Res
import chessclock.shared.generated.resources.digital_7
import chessclock.shared.generated.resources.minecart_lcd

object ChessClockTheme {
    val typography: CustomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalCustomTypography.current
}

@Composable
fun ChessClockTheme(
    content: @Composable () -> Unit
) {
    // 1. Safe runtime resource accessor resolution for KMP common targets
    val dotMatrixFamily = FontFamily(
        Font(resource = Res.font.minecart_lcd)
    )
    val lcdFamily = FontFamily(
        Font(resource = Res.font.LCD14)
    )
    val digitalFamily = FontFamily(
        Font(resource = Res.font.digital_7)
    )

    // 2. Initialize your design token styles
    val customTypography = CustomTypography(
        matrix = TextStyle(
            fontFamily = dotMatrixFamily,
            fontSize = 70.sp
        ),
        lcd = TextStyle(
            fontFamily = lcdFamily,
            fontSize = 40.sp
        ),
        digital = TextStyle(
            fontFamily = digitalFamily,
            fontSize = 50.sp
        )
    )

    // 3. Mount the parameters into the hierarchy context tree
    CompositionLocalProvider(
        LocalCustomTypography provides customTypography
    ) {
        MaterialTheme(
            content = content
        )
    }
}