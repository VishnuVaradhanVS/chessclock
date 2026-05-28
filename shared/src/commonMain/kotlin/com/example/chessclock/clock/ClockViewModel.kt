package com.example.chessclock.clock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ClockViewModel : ViewModel() {
    var whiteTime = mutableStateOf(0L)
    var blackTime = mutableStateOf(0L)
    var whiteInc = mutableStateOf(0L)
    var blackInc = mutableStateOf(0L)
    var currentActive = mutableStateOf(1)
    var dashboard = mutableStateOf(true)
    var whiteFlag = mutableStateOf(false)
    var blackFlag = mutableStateOf(false)
    var flag = mutableStateOf(false)
    var timerActive = mutableStateOf(true)
    var whiteInitial = 0L
    var blackInitial = 0L


    fun updateClockSettings(whiteTime: Long, whiteInc: Long, blackTime: Long, blackInc: Long) {
        this.whiteTime.value = whiteTime
        this.whiteInc.value = whiteInc
        this.blackTime.value = blackTime
        this.blackInc.value = blackInc
        whiteInitial = whiteTime
        blackInitial = blackTime
    }

    fun getCurrentActive(): Int {
        return currentActive.value
    }

    fun toggle() {
        if (currentActive.value == 1) {
            currentActive.value = 0
            if(timerActive.value) whiteTime.value += whiteInc.value
        } else {
            currentActive.value = 1
            if(timerActive.value) blackTime.value += blackInc.value
        }
    }

    suspend fun decrementTime() {
        while (timerActive.value) {
            if (currentActive.value == 1) {
                whiteTime.value -= 1
                if (whiteTime.value == 0L) {
                    whiteFlag.value = true
                    flag.value = true
                    return
                }
            } else {
                blackTime.value -= 1
                if (blackTime.value == 0L) {
                    blackFlag.value = true
                    flag.value = true
                    return
                }
            }
            delay(1000)
        }
    }

    fun toggleTimerState() {
        timerActive.value = !timerActive.value
        if (timerActive.value) {
            viewModelScope.launch { decrementTime() }
        }
    }

    fun resetTimer() {
        whiteTime.value = whiteInitial
        blackTime.value = blackInitial
        currentActive.value = 1
        flag.value=false
        whiteFlag.value=false
        blackFlag.value=false
        timerActive.value=false
    }
}
