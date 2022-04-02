package com.daggery.present.breathpage.entities

import javax.inject.Inject

data class TimerState(
    val duration: Int,
    val state: BreathStateEnum,
    val displayName: String
)

class TimerStatePairBuilder @Inject constructor(){
    private var firstDuration: Int = 0
    private var firstState: BreathStateEnum = BreathStateEnum.GROUND
    private var secondDuration: Int = 0
    private var secondState: BreathStateEnum = BreathStateEnum.GROUND

    private fun reset() {
        firstDuration = 0
        firstState = BreathStateEnum.GROUND
        secondDuration = 0
        secondState = BreathStateEnum.GROUND
    }

    fun setFirstDuration(value: Int): TimerStatePairBuilder {
        firstDuration = value
        return this
    }

    fun setFirstState(value: BreathStateEnum): TimerStatePairBuilder {
        firstState = value
        return this
    }

    fun setSecondDuration(value: Int): TimerStatePairBuilder {
        secondDuration = value
        return this
    }

    fun setSecondState(value: BreathStateEnum): TimerStatePairBuilder {
        secondState = value
        return this
    }

    fun build(): Pair<TimerState, TimerState> {
        val firstDisplayName = firstState.toString().lowercase().replaceFirstChar { it.uppercase() }
        val secondDisplayName = "${secondState.toString().lowercase().replaceFirstChar { it.uppercase() }} ${secondDuration})"

        val pair = Pair(
            TimerState(firstDuration, firstState, firstDisplayName),
            TimerState(secondDuration, secondState, secondDisplayName),
        )

        reset()

        return pair
    }
}
