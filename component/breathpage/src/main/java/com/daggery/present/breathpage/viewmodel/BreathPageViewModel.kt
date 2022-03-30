package com.daggery.present.breathpage.viewmodel

import androidx.lifecycle.ViewModel
import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.breathpage.entities.BreathStateEnum
import com.daggery.present.breathpage.mappers.BreathPatternStateHolderMapper
import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.data.usecases.GetBreathPatternItemByUuidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
    val breathPatternStateHolder get() = BreathPatternStateHolder("0", "Box Breathing", 1, 2, 2, 2, 2, 2)

    private lateinit var timerEngine: TimerEngine
    lateinit var timerState: StateFlow<TimerState>
    lateinit var timerStateFlow: Flow<TimerState>
    private var totalDuration = 1

    private var isSessionActive = true

    suspend fun getBreathPatternStateHolder(uuid: String) {
        _breathPatternStateHolder = getBreathPatternItemByUuidUseCase(uuid)?.let { mapper.toBreathPatternStateHolder(it) }
        if (breathPatternStateHolder != null) {
            timerEngine = TimerEngine(breathPatternStateHolder)
            timerState = timerEngine.timerState
            totalDuration = with(breathPatternStateHolder) {
                return@with (inhaleDuration + holdPostInhaleDuration + exhaleDuration + holdPostExhaleDuration) * repetitions
            }

            timerStateFlow = timerStateFlowBuilder(buildTimerStateList())
        }
    }

    private fun buildTimerStateList(): List<TimerState> {
        val mutableList = mutableListOf<TimerState>()

        mutableList.add(TimerState(3, BreathStateEnum.READY))
        with(breathPatternStateHolder) {
            repeat(breathPatternStateHolder.repetitions) {
                mutableList.add(TimerState(inhaleDuration, BreathStateEnum.INHALE))
                mutableList.add(TimerState(holdPostInhaleDuration, BreathStateEnum.HOLD_POST_INHALE))
                mutableList.add(TimerState(exhaleDuration, BreathStateEnum.EXHALE))
                mutableList.add(TimerState(holdPostExhaleDuration, BreathStateEnum.HOLD_POST_EXHALE))
            }
        }
        mutableList.add(TimerState(1, BreathStateEnum.FINISHED))

        return mutableList.filterNot { it.currentDuration == 0 }
    }

    // TODO: Create animation and data algorithm
    private suspend fun timerStateFlowBuilder(value: List<TimerState>): Flow<TimerState> {
        return flow {
            value.forEach {
                emit(it)
                delay(it.currentDuration * 1000L)
            }
        }
    }

    suspend fun startSession() {
        timerEngine.startTimer()
        isSessionActive = true
    }

    fun stopSession() {
        timerEngine.stopTimer()
    }

    suspend fun resetSession() {
        timerEngine.resetTimer()
    }
}