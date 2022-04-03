package com.daggery.present.breathpage.helper

import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.breathpage.entities.BreathStateEnum
import javax.inject.Inject

class TimerStatePairListBuilder @Inject constructor(
    private val timerStatePairBuilder: TimerStatePairBuilder
) {
    private var _stateHolder: BreathPatternStateHolder? = null
    private val stateHolder get() = _stateHolder!!
    private var mutableList = mutableListOf<Pair<TimerState, TimerState>>()

    private fun reset() {
        _stateHolder = null
        mutableList.clear()
    }

    fun setStateHolder(value: BreathPatternStateHolder): TimerStatePairListBuilder {
        _stateHolder = value
        return this
    }

    // TODO: Fix This Algorithm
    private fun stateCycler(value: BreathStateEnum, repeat: Boolean = true): BreathStateEnum {
        return when(value) {
            BreathStateEnum.INHALE -> {
                return if(stateHolder.holdPostInhaleDuration != 0) BreathStateEnum.HOLD_POST_INHALE
                else BreathStateEnum.EXHALE
            }
            BreathStateEnum.HOLD_POST_INHALE -> BreathStateEnum.EXHALE
            BreathStateEnum.EXHALE -> {
                return if(stateHolder.exhaleDuration != 0) BreathStateEnum.HOLD_POST_EXHALE
                else if(stateHolder.exhaleDuration == 0 && repeat) BreathStateEnum.INHALE
                //else if(stateHolder.exhaleDuration == 0 && !repeat) BreathStateEnum.FINISHED
                else BreathStateEnum.FINISHED
            }
            BreathStateEnum.HOLD_POST_EXHALE -> {
                if (repeat) BreathStateEnum.INHALE
                else BreathStateEnum.FINISHED
            }
            else -> value
        }
    }

    private fun getDuration(state: BreathStateEnum): Int {
        return when(state) {
            BreathStateEnum.INHALE -> stateHolder.inhaleDuration
            BreathStateEnum.HOLD_POST_INHALE -> stateHolder.holdPostInhaleDuration
            BreathStateEnum.EXHALE -> stateHolder.exhaleDuration
            BreathStateEnum.HOLD_POST_EXHALE -> stateHolder.holdPostExhaleDuration
            else -> 0
        }
    }

    fun build(): List<Pair<TimerState, TimerState>> {
        var currentState = BreathStateEnum.INHALE

        mutableList.add(timerStatePairBuilder
            .setFirstState(BreathStateEnum.READY)
            .setFirstDuration(3)
            .setSecondState(BreathStateEnum.INHALE)
            .setSecondDuration(stateHolder.inhaleDuration)
            .build()
        )

        // TODO: Clean Up
        repeat(stateHolder.repetitions) {
            var currentEvaluatedState = BreathStateEnum.INHALE
            val target = if (stateHolder.holdPostExhaleDuration != 0) {
                BreathStateEnum.HOLD_POST_EXHALE
            } else {
                BreathStateEnum.EXHALE
            }

            while(currentEvaluatedState != target) {
                currentEvaluatedState = currentState
                val value = timerStatePairBuilder.setFirstState(currentState)
                value.setFirstDuration(getDuration(currentState))
                currentState = if(it == stateHolder.repetitions - 1) {
                    stateCycler(currentState, false)
                } else {
                    stateCycler(currentState)
                }
                value.setSecondState(currentState)
                value.setSecondDuration(getDuration(currentState))

                mutableList.add(value.build())
            }
        }

        mutableList.add(timerStatePairBuilder
            .setFirstState(BreathStateEnum.FINISHED)
            .setFirstDuration(0)
            .setSecondState(BreathStateEnum.FINISHED)
            .setSecondDuration(0)
            .build()
        )

        val listValue = mutableList.toList()
        reset()
        return listValue
    }
}