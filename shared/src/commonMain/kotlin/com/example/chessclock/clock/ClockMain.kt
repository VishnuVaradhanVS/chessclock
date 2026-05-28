package com.example.chessclock.clock


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun ChessClockMain(clockViewModel: ClockViewModel) {

    Box(modifier = Modifier.fillMaxSize().background(color = Color.Gray)) {
        if (clockViewModel.dashboard.value) {
            Dashboard(clockViewModel = clockViewModel)
        } else {
            ChessClock(clockViewModel)
        }
    }
}

