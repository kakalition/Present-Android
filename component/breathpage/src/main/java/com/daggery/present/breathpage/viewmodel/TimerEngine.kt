package com.daggery.present.breathpage.viewmodel

import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.breathpage.entities.BreathStateEnum
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class TimerState(
    val currentDuration: Int,
    val currentState: BreathStateEnum
)

class TimerEngine @Inject constructor(
    private var breathPattern: BreathPatternStateHolder
){

    private var _timerState = MutableStateFlow(TimerState(0, BreathStateEnum.GROUND))
    val timerState = _timerState.asStateFlow()

    private val totalDuration = with(breathPattern) {
        return@with (inhaleDuration +
                holdPostInhaleDuration +
                exhaleDuration +
                holdPostExhaleDuration
                ).times(repetitions)
            .plus(3)
    }

    private val clockEngine = ClockEngine(
        totalDurationMs = totalDuration * 1000,
        action = { tickTime() }
    )

    suspend fun startTimer() {
        clockEngine.startTimer()
        // Emit BreathStateEnum.FINISHED to indicate timer has reached end.
        _timerState.emit(timerState.value.copy(
            currentDuration = 0,
            currentState = BreathStateEnum.FINISHED
        ))
    }

    fun stopTimer() {
        clockEngine.stopTimer()
    }

    suspend fun resetTimer() {
        _timerState.emit(timerState.value.copy(
            currentDuration = 0,
            currentState = BreathStateEnum.GROUND)
        )
        clockEngine.resetTimer()
    }

    private fun cycleTimerState(value: TimerState): TimerState {
        return when {
            value.currentState == BreathStateEnum.GROUND -> {
                value.copy(currentDuration = 3, currentState = BreathStateEnum.READY)
            }
            value.currentState == BreathStateEnum.READY && value.currentDuration == 3 -> {
                value.copy(currentState = BreathStateEnum.READY)
            }
            value.currentState == BreathStateEnum.READY && value.currentDuration == 2 -> {
                value.copy(currentState = BreathStateEnum.READY)
            }
            value.currentState == BreathStateEnum.READY && value.currentDuration == 1 -> {
                value.copy(currentState = BreathStateEnum.READY)
            }
            value.currentState == BreathStateEnum.READY && value.currentDuration == 0 -> {
                value.copy(currentDuration = breathPattern.inhaleDuration, currentState = BreathStateEnum.INHALE)
            }
            value.currentState == BreathStateEnum.INHALE && value.currentDuration == 0 -> {
                value.copy(
                    currentDuration = breathPattern.holdPostInhaleDuration,
                    currentState = BreathStateEnum.HOLD_POST_INHALE
                )
            }
            value.currentState == BreathStateEnum.HOLD_POST_INHALE && value.currentDuration == 0 -> {
                value.copy(
                    currentDuration = breathPattern.exhaleDuration,
                    currentState = BreathStateEnum.EXHALE
                )
            }
            value.currentState == BreathStateEnum.EXHALE && value.currentDuration == 0 -> {
                value.copy(
                    currentDuration = breathPattern.holdPostExhaleDuration,
                    currentState = BreathStateEnum.HOLD_POST_EXHALE
                )
            }
            value.currentState == BreathStateEnum.HOLD_POST_EXHALE && value.currentDuration == 0 -> {
                value.copy(
                    currentDuration = breathPattern.inhaleDuration,
                    currentState = BreathStateEnum.INHALE
                )
            }
            else -> { value }
        }
    }

    private suspend fun tickTime() {
        val currentDuration = timerState.value.currentDuration - 1
        val currentTimerState = cycleTimerState(timerState.value.copy(currentDuration = currentDuration))
        _timerState.emit(currentTimerState)
    }

}

private class ClockEngine(
    private val totalDurationMs: Int,
    private val action: suspend () -> Unit,
) {

    private var currentTimeMs = 0L

    private var secondIndicator = 0
    private val secondFullIndicator = 10

    private var requestStop = false

    suspend fun startTimer() {
        while(currentTimeMs <= totalDurationMs) {
            if(requestStop) {
                requestStop = false
                break
            }

            delay(100)
            currentTimeMs += 100
            secondIndicator += 1

            if(secondIndicator == secondFullIndicator) {
                action()
                secondIndicator = 0
            }
        }
    }

    fun stopTimer() {
        requestStop = true
    }

    fun resetTimer() {
        currentTimeMs = 0L
        stopTimer()
    }
}