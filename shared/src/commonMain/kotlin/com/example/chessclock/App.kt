package com.example.chessclock

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import chessclock.shared.generated.resources.Res
import com.example.chessclock.clock.ChessClockMain
import com.example.chessclock.clock.ClockViewModel
import com.example.chessclock.ui.theme.ChessClockTheme

@Composable
@Preview
fun App() {
    val clockViewModel = ClockViewModel()
    ChessClockTheme{
        ChessClockMain(clockViewModel)
    }
}
