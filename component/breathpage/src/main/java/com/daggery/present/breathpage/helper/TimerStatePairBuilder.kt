package com.daggery.present.breathpage.helper

import com.daggery.present.breathpage.entities.BreathStateEnum
import javax.inject.Inject

data class TimerState(
    val duration: Int,
    val state: BreathStateEnum,
    val displayName: String
)

private enum class Position {
    FIRST,
    SECOND
}

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

    private fun formatDisplayName(state: BreathStateEnum, position: Position, withDuration: Boolean): String {
        var formattedName = ""
        formattedName += when(state) {
            BreathStateEnum.GROUND -> ""
            BreathStateEnum.READY -> "Ready "
            BreathStateEnum.INHALE -> "Inhale "
            BreathStateEnum.HOLD_POST_INHALE -> "Hold "
            BreathStateEnum.EXHALE -> "Exhale "
            BreathStateEnum.HOLD_POST_EXHALE -> "Hold "
            BreathStateEnum.FINISHED -> "Finish "
        }
        if(withDuration) {
            if(position == Position.FIRST) formattedName += "(${firstDuration})"
             else formattedName += "(${secondDuration})"
        }
        return formattedName
    }

    fun build(): Pair<TimerState, TimerState> {
        val firstDisplayName = formatDisplayName(firstState, Position.FIRST, false)
        val secondDisplayName = formatDisplayName(secondState, Position.SECOND, true)

        val pair = Pair(
            TimerState(firstDuration, firstState, firstDisplayName),
            TimerState(secondDuration, secondState, secondDisplayName),
        )

        reset()

        return pair
    }
}
