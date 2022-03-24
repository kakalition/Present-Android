package com.daggery.present.breathpage.viewmodel

import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.breathpage.entities.BreathStateEnum
import kotlinx.coroutines.delay
import javax.inject.Inject

// TODO: SUBJECT TO CHANGE

class TimerEngine @Inject constructor(
    private var breathPattern: BreathPatternStateHolder
){

    private var currentDuration = 0
    private val totalDuration = with(breathPattern) {
        return@with (inhaleDuration + holdPostInhaleDuration + exhaleDuration + holdPostExhaleDuration) * repetitions
    }
    private var stateSequence = listOf<Int>()

    private var clockEngine = ClockEngine(
        totalDurationMs = totalDuration * 1000,
        action = { tickTime() }
    )

    private fun cycleState() {
        breathPattern = breathPattern.copy(
            state = when(breathPattern.state) {
                BreathStateEnum.INHALE -> BreathStateEnum.HOLD_POST_INHALE
                BreathStateEnum.HOLD_POST_INHALE -> BreathStateEnum.EXHALE
                BreathStateEnum.EXHALE -> BreathStateEnum.HOLD_POST_EXHALE
                BreathStateEnum.HOLD_POST_EXHALE -> BreathStateEnum.INHALE
                else -> BreathStateEnum.FINISHED
            }
        )
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

    private fun tickTime() {
        println("State: ${breathPattern.state} | Duration: ${currentDuration + 1}")
        currentDuration++
        if(stateSequence.contains(currentDuration)) {
            cycleState()
        }
    }

    suspend fun startTimer() {
        clockEngine.startTimer()
    }

    fun stopTimer() {
        clockEngine.stopTimer()
    }

    fun resetTimer() {
        currentDuration = 0
        breathPattern = breathPattern.copy(state = BreathStateEnum.INHALE)
        clockEngine.resetTimer()
    }


}

private class ClockEngine(
    private val totalDurationMs: Int,
    private val action: () -> Unit,
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