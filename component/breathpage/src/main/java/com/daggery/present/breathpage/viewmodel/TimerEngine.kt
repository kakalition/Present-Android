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

    private var _timerState = MutableStateFlow(TimerState(1, BreathStateEnum.INHALE))
    val timerState = _timerState.asStateFlow()

    private var currentDuration = 1
    private val totalDuration = with(breathPattern) {
        return@with (inhaleDuration + holdPostInhaleDuration + exhaleDuration + holdPostExhaleDuration) * repetitions
    }
    private var stateSequence = generateList()

    private var clockEngine = ClockEngine(
        totalDurationMs = totalDuration * 1000,
        action = { tickTime() }
    )

    private fun cycleState(breathStateEnum: BreathStateEnum): BreathStateEnum {
        return when(breathStateEnum) {
            BreathStateEnum.INHALE -> BreathStateEnum.HOLD_POST_INHALE
            BreathStateEnum.HOLD_POST_INHALE -> BreathStateEnum.EXHALE
            BreathStateEnum.EXHALE -> BreathStateEnum.HOLD_POST_EXHALE
            BreathStateEnum.HOLD_POST_EXHALE -> BreathStateEnum.INHALE
            else -> BreathStateEnum.FINISHED
        }
    }

    private fun generateList(): List<Int> {
        var currentDuration = 0
        val value = mutableListOf<Int>()
        while (currentDuration != (totalDuration)) {
            currentDuration += breathPattern.inhaleDuration
            value.add(currentDuration)
            currentDuration += breathPattern.holdPostInhaleDuration
            value.add(currentDuration)
            currentDuration += breathPattern.exhaleDuration
            value.add(currentDuration)
            currentDuration += breathPattern.holdPostExhaleDuration
            value.add(currentDuration)
        }
        return value
    }

    private suspend fun tickTime() {
        currentDuration++
        var currentState = timerState.value.currentState
        if(stateSequence.contains(currentDuration)) {
            currentState = cycleState(currentState)
        }

        _timerState.emit(timerState.value.copy(
            currentDuration = currentDuration,
            currentState = currentState
        ))
    }

    suspend fun startTimer() {
        return clockEngine.startTimer()
    }

    fun stopTimer() {
        clockEngine.stopTimer()
    }

    suspend fun resetTimer() {
        _timerState.emit(timerState.value.copy(
            currentDuration = 0,
            currentState = BreathStateEnum.INHALE)
        )
        clockEngine.resetTimer()
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