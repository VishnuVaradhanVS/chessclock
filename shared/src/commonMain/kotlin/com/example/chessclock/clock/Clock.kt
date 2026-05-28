package com.example.chessclock.clock

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonShapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ChessClock(clockViewModel: ClockViewModel) {
    val showHoursWhite = mutableStateOf(
        if (clockViewModel.whiteTime.value / 3600 > 0L) true else false
    )
    val showHoursBlack = mutableStateOf(
        if (clockViewModel.blackTime.value / 3600 > 0L) true else false
    )

    LaunchedEffect(clockViewModel) {
        clockViewModel.decrementTime()
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxWidth().weight(0.45f)
                    .background(if (clockViewModel.flag.value) Color.Red else if (clockViewModel.getCurrentActive() == 1) Color.LightGray else Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxSize().padding(60.dp).shadow(
                        32.dp,
                        RoundedCornerShape(32.dp),
                        ambientColor = Color.White,
                        spotColor = Color.White
                    ),
                    shape = RoundedCornerShape(32.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 32.dp),
                    colors = CardDefaults.elevatedCardColors()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(if (clockViewModel.flag.value) Color.DarkGray else Color.LightGray)
                            .clickable(onClick = {
                                if (clockViewModel.currentActive.value == 0) clockViewModel.toggle()
                            }), contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "${if ((clockViewModel.blackTime.value % 3600) / 60 < 10) "0" else ""}${(clockViewModel.blackTime.value % 3600) / 60}:${if (clockViewModel.blackTime.value % 60 < 10) "0" else ""}${clockViewModel.blackTime.value % 60}",
                                color = if (clockViewModel.blackFlag.value) Color.Red else if (clockViewModel.flag.value) Color.White else Color.Black,
                                fontSize = if (showHoursBlack.value) 70.sp else 80.sp,
                                style = com.example.chessclock.ui.theme.ChessClockTheme.typography.lcd,
                                modifier = Modifier.scale(-1f,-1f)
                            )
                            if (showHoursBlack.value) {
                                Text(
                                    text = "${if (clockViewModel.blackTime.value / 3600 < 10) "0" else ""}${clockViewModel.blackTime.value / 3600}",
                                    color = if (clockViewModel.blackFlag.value) Color.Red else if (clockViewModel.flag.value) Color.White else Color.Black,
                                    fontSize = 160.sp,
                                    style = com.example.chessclock.ui.theme.ChessClockTheme.typography.lcd,
                                    modifier = Modifier.scale(-1f,-1f)
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth().weight(0.45f)
                    .background(if (clockViewModel.flag.value) Color.Red else if (clockViewModel.getCurrentActive() == 1) Color.LightGray else Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxSize().padding(60.dp).shadow(
                        32.dp,
                        RoundedCornerShape(32.dp),
                        ambientColor = Color.Black,
                        spotColor = Color.Black
                    ),
                    shape = RoundedCornerShape(32.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 32.dp),
                    colors = CardDefaults.elevatedCardColors()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(if (clockViewModel.flag.value) Color.LightGray else Color.DarkGray)
                            .clickable(onClick = {
                                if (clockViewModel.currentActive.value == 1) clockViewModel.toggle()
                            }), contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (showHoursWhite.value) {
                                Text(
                                    text = "${if (clockViewModel.whiteTime.value / 3600 < 10) "0" else ""}${clockViewModel.whiteTime.value / 3600}",
                                    color = if (clockViewModel.whiteFlag.value) Color.Red else if (clockViewModel.flag.value) Color.Black else Color.White,
                                    fontSize = 160.sp,
                                    style = com.example.chessclock.ui.theme.ChessClockTheme.typography.lcd
                                )
                            }
                            Text(
                                text = "${if ((clockViewModel.whiteTime.value % 3600) / 60 < 10) "0" else ""}${(clockViewModel.whiteTime.value % 3600) / 60}:${if (clockViewModel.whiteTime.value % 60 < 10) "0" else ""}${clockViewModel.whiteTime.value % 60}",
                                color = if (clockViewModel.whiteFlag.value) Color.Red else if (clockViewModel.flag.value) Color.Black else Color.White,
                                fontSize = if (showHoursWhite.value) 70.sp else 80.sp,
                                style = com.example.chessclock.ui.theme.ChessClockTheme.typography.lcd
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier.background(if (clockViewModel.flag.value) Color.Red else if (clockViewModel.getCurrentActive() == 1) Color.LightGray else Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(15.dp, 0.dp).clickable(onClick = {
                        clockViewModel.resetTimer()
                    }),
                    imageVector = Icons.Filled.Repeat,
                    contentDescription = "Reset",
                    tint = if (clockViewModel.getCurrentActive() == 1) Color.Black else Color.White
                )
                Icon(
                    modifier = Modifier.padding(15.dp, 0.dp).clickable(onClick = {
                        clockViewModel.toggleTimerState()
                    }),
                    imageVector = if (clockViewModel.timerActive.value) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = "Pause/Play",
                    tint = if (clockViewModel.getCurrentActive() == 1) Color.Black else Color.White
                )
                Icon(
                    modifier = Modifier.padding(15.dp, 0.dp).clickable(onClick = {
                        clockViewModel.dashboard.value = true
                    }),
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Exit",
                    tint = if (clockViewModel.getCurrentActive() == 1) Color.Black else Color.White
                )
            }
        }
    }
}



