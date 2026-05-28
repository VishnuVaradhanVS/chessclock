package com.example.chessclock.clock


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable

fun Dashboard(modifier: Modifier = Modifier, clockViewModel: ClockViewModel) {
    val visibleDashboard = rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val hours = (0..99).toList()
    val minutes = (0..59).toList()
    val seconds = (0..59).toList()

    val blackHr = rememberSaveable { mutableStateOf(0L) }
    val blackMin = rememberSaveable { mutableStateOf(0L) }
    val blackSec = rememberSaveable { mutableStateOf(0L) }
    val blackIncMin = rememberSaveable { mutableStateOf(0L) }
    val blackIncSec = rememberSaveable { mutableStateOf(0L) }

    val whiteHr = rememberSaveable { mutableStateOf(0L) }
    val whiteMin = rememberSaveable { mutableStateOf(0L) }
    val whiteSec = rememberSaveable { mutableStateOf(0L) }
    val whiteIncMin = rememberSaveable { mutableStateOf(0L) }
    val whiteIncSec = rememberSaveable { mutableStateOf(0L) }

    val invalidTimeBlack = rememberSaveable{ mutableStateOf(false)}
    val invalidTimeWhite = rememberSaveable{ mutableStateOf(false)}


    LaunchedEffect(Unit) {
        visibleDashboard.value = true
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent), contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visibleDashboard.value,
            modifier = Modifier.align(Alignment.TopCenter),
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(800)
            ),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(800)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(Color.DarkGray).padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().background(Color.Transparent),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().weight(0.5f).clip(
                            RoundedCornerShape(32.dp)
                        ).border(2.dp, Color.White, RoundedCornerShape(32.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Base Time",
                                color = Color.White,
                                style = com.example.chessclock.ui.theme.ChessClockTheme.typography.digital,
                                modifier = Modifier.padding(8.dp)
                            )
                            Box(
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(32.dp))
                                    .background(Color.White), contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TimePickerWheel(
                                        items = hours,
                                        onItemSelect = { blackHr.value = it },
                                        modifier = Modifier.weight(1f),
                                        color=0,
                                        isBase = true,
                                        offset = 0
                                    )
                                    Text(
                                        ":",
                                        style = com.example.chessclock.ui.theme.ChessClockTheme.typography.digital,
                                        fontSize = 90.sp
                                    )
                                    TimePickerWheel(
                                        items = minutes,
                                        onItemSelect = { blackMin.value = it },
                                        modifier = Modifier.weight(1f),
                                        color=0,
                                        isBase = true,
                                        offset = 0
                                    )
                                    Text(
                                        ":",
                                        style = com.example.chessclock.ui.theme.ChessClockTheme.typography.digital,
                                        fontSize = 90.sp
                                    )
                                    TimePickerWheel(
                                        items = seconds,
                                        onItemSelect = { blackSec.value = it },
                                        modifier = Modifier.weight(1f),
                                        color=0,
                                        isBase = true,
                                        offset = 0
                                    )
                                }
                            }
                        }
                    }

                    if(invalidTimeBlack.value){
                        Text(text = "Select a valid base time",
                            color = Color.Red,
                            style = com.example.chessclock.ui.theme.ChessClockTheme.typography.matrix,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(0.dp,10.dp,0.dp,0.dp))
                    }
                    else{
                        Spacer(modifier = Modifier.fillMaxWidth().weight(0.1f))
                    }
                    Box(
                        modifier = Modifier.fillMaxSize().padding(48.dp, 16.dp).weight(0.4f).clip(
                            RoundedCornerShape(32.dp)
                        ).border(2.dp, Color.White, RoundedCornerShape(32.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(32.dp))
                                    .background(Color.White).weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TimePickerWheel(
                                        items = hours,
                                        onItemSelect = { blackIncMin.value = it },
                                        modifier = Modifier.weight(1f),
                                        color=0,
                                        isBase = false,
                                        offset = 15
                                    )
                                    Text(
                                        ":",
                                        style = com.example.chessclock.ui.theme.ChessClockTheme.typography.digital,
                                        fontSize = 60.sp
                                    )
                                    TimePickerWheel(
                                        items = minutes,
                                        onItemSelect = { blackIncSec.value = it },
                                        modifier = Modifier.weight(1f),
                                        color=0,
                                        isBase = false,
                                        offset = 15
                                    )
                                }
                            }
                            Text(
                                "Increment",
                                color = Color.White,
                                style = com.example.chessclock.ui.theme.ChessClockTheme.typography.digital,
                                modifier = Modifier.padding(8.dp).fillMaxSize().weight(0.6f),
                                textAlign = TextAlign.Center,
                                fontSize = 40.sp
                            )
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = visibleDashboard.value,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(800)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(800)
            )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(Color.LightGray).padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().background(Color.Transparent),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(48.dp, 16.dp).weight(0.4f).clip(
                            RoundedCornerShape(32.dp)
                        ).border(2.dp, Color.Black, RoundedCornerShape(32.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "Increment",
                                color = Color.Black,
                                style = com.example.chessclock.ui.theme.ChessClockTheme.typography.digital,
                                modifier = Modifier.padding(8.dp).fillMaxSize().weight(0.6f),
                                textAlign = TextAlign.Center,
                                fontSize = 40.sp
                            )
                            Box(
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(32.dp))
                                    .background(Color.Black).weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TimePickerWheel(
                                        items = hours,
                                        onItemSelect = { whiteIncMin.value = it },
                                        modifier = Modifier.weight(1f),
                                        color=1,
                                        isBase = false,
                                        offset = 25
                                    )
                                    Text(
                                        ":",
                                        style = com.example.chessclock.ui.theme.ChessClockTheme.typography.digital,
                                        color = Color.White,
                                        fontSize = 60.sp
                                    )
                                    TimePickerWheel(
                                        items = minutes,
                                        onItemSelect = { whiteIncSec.value = it },
                                        modifier = Modifier.weight(1f),
                                        color=1,
                                        isBase = false,
                                        offset = 25
                                    )
                                }
                            }

                        }
                    }
                    if(invalidTimeWhite.value){
                        Text(text = "Select a valid base time",
                            color = Color.Red,
                            style = com.example.chessclock.ui.theme.ChessClockTheme.typography.matrix,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(0.dp,0.dp,0.dp,10.dp))
                    }
                    else{
                        Spacer(modifier = Modifier.fillMaxWidth().weight(0.1f))
                    }
                    Box(
                        modifier = Modifier.fillMaxSize().weight(0.5f).clip(
                            RoundedCornerShape(32.dp)
                        ).border(2.dp, Color.Black, RoundedCornerShape(32.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(32.dp))
                                    .background(Color.Black).weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Hours Wheel
                                    TimePickerWheel(
                                        items = hours,
                                        onItemSelect = { whiteHr.value = it },
                                        modifier = Modifier.weight(1f),
                                        color=1,
                                        isBase = true,
                                        offset = 0
                                    )
                                    Text(
                                        ":",
                                        style = com.example.chessclock.ui.theme.ChessClockTheme.typography.digital,
                                        color = Color.White,
                                        fontSize = 90.sp
                                    )

                                    // Minutes Wheel
                                    TimePickerWheel(
                                        items = minutes,
                                        onItemSelect = { whiteMin.value = it },
                                        modifier = Modifier.weight(1f),
                                        color=1,
                                        isBase = true,
                                        offset = 0
                                    )

                                    Text(
                                        ":",
                                        style = com.example.chessclock.ui.theme.ChessClockTheme.typography.digital,
                                        color = Color.White,
                                        fontSize = 90.sp
                                    )

                                    // Seconds Wheel
                                    TimePickerWheel(
                                        items = seconds,
                                        onItemSelect = { whiteSec.value = it },
                                        modifier = Modifier.weight(1f),
                                        color = 1,
                                        isBase = true,
                                        offset = 0
                                    )
                                }
                            }
                            Text(
                                "Base Time",
                                color = Color.Black,
                                style = com.example.chessclock.ui.theme.ChessClockTheme.typography.digital,
                                modifier = Modifier.padding(8.dp).weight(0.4f)
                            )
                        }
                    }

                }
            }
        }
        Box(
            modifier = Modifier.align(Alignment.Center)
                .shadow(elevation = 12.dp, shape = CircleShape).clip(CircleShape)
                // Uses a gradient with sharp color stops to create a crisp 50/50 split line
                .background(
                    brush = androidx.compose.ui.graphics.Brush.linearGradient(
                        0.0f to Color.White,   // Top half is White
                        0.5f to Color.White,   // Split point
                        0.5f to Color.Black,   // Split point switches to Black
                        1.0f to Color.Black,   // Bottom half is Black
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY) // Dynamic vertical drop
                    )
                ).clickable {
                    if(whiteHr.value == 0L && whiteMin.value == 0L && whiteSec.value == 0L){
                        invalidTimeWhite.value=true
                    }
                    else{
                        invalidTimeWhite.value=false
                    }
                    if(blackHr.value == 0L && blackMin.value == 0L && blackSec.value == 0L){
                        invalidTimeBlack.value=true
                    }
                    else{
                        invalidTimeBlack.value=false
                    }
                    if(!invalidTimeWhite.value && !invalidTimeBlack.value){
                        coroutineScope.launch {
                            visibleDashboard.value = false
                            delay(800)
                            clockViewModel.resetTimer()
                            clockViewModel.updateClockSettings((whiteHr.value*3600)+(whiteMin.value*60)+whiteSec.value, (whiteIncMin.value*60)+whiteIncSec.value, (blackHr.value*3600)+(blackMin.value*60)+blackSec.value, (blackIncMin.value*60)+blackIncSec.value)
                            clockViewModel.dashboard.value = false
                        }
                    }
                }.padding(horizontal = 16.dp, vertical = 8.dp), contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "START", color = Color.Black,
                    fontSize = 24.sp, fontWeight = FontWeight.ExtraBold,
                    style = com.example.chessclock.ui.theme.ChessClockTheme.typography.matrix
                )
                Text(
                    text = "GAME", color = Color.White,
                    fontSize = 24.sp, fontWeight = FontWeight.Bold,
                    style = com.example.chessclock.ui.theme.ChessClockTheme.typography.matrix

                )
            }
        }
    }
}


@Composable
fun TimePickerWheel(
    items: List<Int>,
    onItemSelect: (Long) -> Unit,
    modifier: Modifier = Modifier,
    color: Int,
    isBase: Boolean,
    offset: Int
) {
    val listState = rememberLazyListState(0,offset)
    // rememberSnapFlingBehavior ensures the list snaps cleanly to an item when scrolling stops
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    // Define a fixed row height that fits your 90.sp font comfortably
    val rowHeight = if(isBase)110.dp else 90.dp

    // Listen to scroll state changes and update your time values
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                onItemSelect(items[index].toLong())
            }
    }

    LazyColumn(
        state = listState,
        flingBehavior = snapFlingBehavior,
        modifier = modifier
            .height(rowHeight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items) { item ->
            Box(
                modifier = Modifier.height(rowHeight), // Ensure the item matches the container height
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (item < 10) "0$item" else item.toString(),
                    fontSize = if(isBase)90.sp else 70.sp,
                    style = com.example.chessclock.ui.theme.ChessClockTheme.typography.digital,
                    color = if(color==0) Color.Black else Color.White // Adjust color based on your container background
                )
            }
        }
    }
}