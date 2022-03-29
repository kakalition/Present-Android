package com.daggery.present.breathpage.viewmodel

import androidx.lifecycle.ViewModel
import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.breathpage.entities.BreathStateEnum
import com.daggery.present.breathpage.mappers.BreathPatternStateHolderMapper
import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.data.usecases.GetBreathPatternItemByUuidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class NextStateHolder(
    val nextState: BreathStateEnum,
    val duration: Int
) {
    override fun toString(): String {
        val state = nextState.name.lowercase().replaceFirstChar { it.uppercase() }
        return "Next: $state ($duration)"
    }
}

@HiltViewModel
class BreathPageViewModel @Inject constructor(
    private val mapper: BreathPatternStateHolderMapper,
    private val getBreathPatternItemByUuidUseCase: GetBreathPatternItemByUuidUseCase
): ViewModel() {

    private var _breathPatternStateHolder: BreathPatternStateHolder? = null
    val breathPatternStateHolder get() = _breathPatternStateHolder!!

    private lateinit var timerEngine: TimerEngine
    lateinit var timerState: StateFlow<TimerState>
    private var totalDuration = 1

    private fun createStateList(): MutableList<NextStateHolder> {
        val tempList = mutableListOf<NextStateHolder>()

        repeat(breathPatternStateHolder.repetitions) {
            with(breathPatternStateHolder) {
                tempList.add(NextStateHolder(state, inhaleDuration))
                tempList.add(NextStateHolder(state, holdPostInhaleDuration))
                tempList.add(NextStateHolder(state, exhaleDuration))
                tempList.add(NextStateHolder(state, holdPostExhaleDuration))
                if((it - 1) == breathPatternStateHolder.repetitions) tempList.add(NextStateHolder(BreathStateEnum.FINISHED, 0))
            }
        }
        return tempList.filterNot { it.duration == 0 }.toMutableList()

    }

    suspend fun getBreathPatternStateHolder(uuid: String) {
        // Test Only
/*
        _breathPatternStateHolder =
            getBreathPatternItemByUuidUseCase(uuid)?.let { mapper.toBreathPatternStateHolder(it) }
                ?: mapper.toBreathPatternStateHolder(
                    BreathPatternItem(
                        "0", "Box Breathing", 1, 1, 1, 1, 1, 1
                    )
                )

*/
        // Real Implementation
        _breathPatternStateHolder = getBreathPatternItemByUuidUseCase(uuid)?.let { mapper.toBreathPatternStateHolder(it) }
        if (_breathPatternStateHolder != null) {
            timerEngine = TimerEngine(breathPatternStateHolder)
            timerState = timerEngine.timerState
            totalDuration = with(breathPatternStateHolder) {
                return@with (inhaleDuration + holdPostInhaleDuration + exhaleDuration + holdPostExhaleDuration) * repetitions
            }
        }
    }

    suspend fun startSession() {
        timerEngine.startTimer()
    }

    fun stopSession() {
        timerEngine.stopTimer()
    }

    suspend fun resetSession() {
        timerEngine.resetTimer()
    }
}