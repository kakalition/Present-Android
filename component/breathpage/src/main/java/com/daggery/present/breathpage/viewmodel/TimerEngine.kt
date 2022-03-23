package com.daggery.present.breathpage.viewmodel

import com.daggery.present.breathpage.entities.ActiveBreathPatternItem
import com.daggery.present.breathpage.entities.BreathStateEnum
import kotlinx.coroutines.delay
import javax.inject.Inject

// TODO: SUBJECT TO CHANGE

class TimerEngine @Inject constructor(){

    private var _breathPattern: ActiveBreathPatternItem? = null
    private val breathPattern get() = _breathPattern!!

    private var currentDuration = 0
    private var totalDurationMs = 0L
    private var stateSequence = listOf<Int>()

    private var clockEngine = ClockEngine(
        resetTimerAction = { resetTimer() },
        action = { tickTime() }
    )

    fun setBreathPattern(value: ActiveBreathPatternItem) {
        _breathPattern = value
        totalDurationMs = (value.inhaleDuration + value.holdPostInhaleDuration + value.exhaleDuration +
                value.holdPostExhaleDuration) * value.repetitions * 1000L
        stateSequence = generateList()
        clockEngine.setTotalDurationMs(totalDurationMs)
    }

    fun removeBreathPattern() {
        _breathPattern = null
        totalDurationMs = 0
    }

    suspend fun startTimer() {
        clockEngine.startTimer()
    }

    private fun cycleState() {
        breathPattern.state = when(breathPattern.state) {
            BreathStateEnum.INHALE -> BreathStateEnum.HOLD_POST_INHALE
            BreathStateEnum.HOLD_POST_INHALE -> BreathStateEnum.EXHALE
            BreathStateEnum.EXHALE -> BreathStateEnum.HOLD_POST_EXHALE
            BreathStateEnum.HOLD_POST_EXHALE -> BreathStateEnum.INHALE
        }
    }

    private fun generateList(): List<Int> {
        var currentDuration = 0
        val value = mutableListOf<Int>()
        while(currentDuration != (totalDurationMs/1000).toInt()){
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

    fun pauseTimer() {
        clockEngine.pauseTimer()
    }

    suspend fun resumeTimer() {
        clockEngine.resumeTimer()
    }

    fun resetTimer() {
        currentDuration = 0
        breathPattern.state = BreathStateEnum.INHALE
        clockEngine.resetTimer()
    }


}

private class ClockEngine(
    private val resetTimerAction: () -> Unit,
    private val action: () -> Unit,
) {

    private var _totalDurationMs: Long? = null
    private val totalDurationMs get() = _totalDurationMs!!
    private var currentTime = 0L

    private var secondIndicator = 0
    private val secondFullIndicator = 10

    private var requestPause = false

    fun setTotalDurationMs(value: Long) {
        _totalDurationMs = value
    }

    suspend fun startTimer() {
        while(currentTime <= totalDurationMs) {

            if(requestPause) {
                requestPause = false
                break
            }

            delay(100)
            currentTime += 100

            secondIndicator += 1

            if(secondIndicator == secondFullIndicator) {
                action()
                secondIndicator = 0
            }
        }
    }

    fun pauseTimer() {
        requestPause = true
    }

    suspend fun resumeTimer() {
        startTimer()
    }

    fun resetTimer() {
        currentTime = 0L
        pauseTimer()
    }
}