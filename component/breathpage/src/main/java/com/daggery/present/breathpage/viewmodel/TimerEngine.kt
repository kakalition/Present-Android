package com.daggery.present.breathpage.viewmodel

import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.breathpage.entities.BreathStateEnum
import com.daggery.present.breathpage.entities.TimerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TimerEngine @Inject constructor(
    private var breathPattern: BreathPatternStateHolder
){

    private var _rawTimerState = MutableStateFlow(TimerState(0, BreathStateEnum.GROUND, ""))

    private var _timerState = MutableStateFlow(TimerState(0, BreathStateEnum.GROUND, ""))
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
        _timerState.emit(timerState.value.copy(duration = 3, state = BreathStateEnum.READY))
        _rawTimerState.emit(timerState.value.copy(duration = 3, state = BreathStateEnum.READY))
        val isReset = clockEngine.startTimer()

        //delay
        //delay()
        // Emit BreathStateEnum.FINISHED to indicate timer has reached end.
        if(!isReset) {
            _timerState.emit(timerState.value.copy(
                duration = 0,
                state = BreathStateEnum.FINISHED
            ))
        }
    }

    fun stopTimer() {
        clockEngine.stopTimer()
    }

    suspend fun resetTimer() {
        _timerState.emit(timerState.value.copy(
            duration = 0,
            state = BreathStateEnum.GROUND)
        )
        clockEngine.resetTimer()
    }

    private fun cycleTimerState(value: TimerState): TimerState {
        return when {
            value.state == BreathStateEnum.READY && value.duration == 0 -> {
                value.copy(duration = breathPattern.inhaleDuration, state = BreathStateEnum.INHALE)
            }
            value.state == BreathStateEnum.INHALE && value.duration == 0 -> {
                value.copy(
                    duration = breathPattern.holdPostInhaleDuration,
                    state = BreathStateEnum.HOLD_POST_INHALE
                )
            }
            value.state == BreathStateEnum.HOLD_POST_INHALE && value.duration == 0 -> {
                value.copy(
                    duration = breathPattern.exhaleDuration,
                    state = BreathStateEnum.EXHALE
                )
            }
            value.state == BreathStateEnum.EXHALE && value.duration == 0 -> {
                value.copy(
                    duration = breathPattern.holdPostExhaleDuration,
                    state = BreathStateEnum.HOLD_POST_EXHALE
                )
            }
            value.state == BreathStateEnum.HOLD_POST_EXHALE && value.duration == 0 -> {
                value.copy(
                    duration = breathPattern.inhaleDuration,
                    state = BreathStateEnum.INHALE
                )
            }
            else -> { value }
        }
    }

    // TODO: Issue: last second emission not delayed
    private suspend fun tickTime() {
        val currentDuration = _rawTimerState.value.duration - 1
        val currentTimerState = cycleTimerState(_rawTimerState.value.copy(duration = currentDuration))
        _rawTimerState.value = currentTimerState
        if(timerState.value.state != _rawTimerState.value.state) {
            _timerState.emit(_rawTimerState.value)
        }
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
    private var requestReset = false

    // If reset timer return true, else false
    suspend fun startTimer(): Boolean {
        while (currentTimeMs <= totalDurationMs) {
            if (requestStop) {
                requestStop = false
                break
            } else if (requestReset) {
                requestReset = false
                currentTimeMs = 0L
                break
            }

            delay(100)
            currentTimeMs += 100
            secondIndicator += 1

            if (secondIndicator == secondFullIndicator) {
                action()
                secondIndicator = 0
            }
        }
        return currentTimeMs == 0L
    }

    fun stopTimer() {
        requestStop = true
    }

    fun resetTimer() {
        requestReset = true
    }
}