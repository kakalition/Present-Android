package com.daggery.present.breathpage.viewmodel

import androidx.lifecycle.ViewModel
import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.breathpage.entities.BreathStateEnum
import com.daggery.present.breathpage.mappers.BreathPatternStateHolderMapper
import com.daggery.present.domain.usecases.GetBreathPatternItemByUuidUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BreathPageViewModel @Inject constructor(
    private val mapper: BreathPatternStateHolderMapper,
    private val getBreathPatternItemByUuidUseCase: GetBreathPatternItemByUuidUseCase
): ViewModel() {

    private var _breathPatternStateHolder: BreathPatternStateHolder? = null
    val breathPatternStateHolder get() = _breathPatternStateHolder!!

    private lateinit var timerEngine: TimerEngine
    lateinit var timerState: StateFlow<TimerState>
    private var totalDuration = 1

    suspend fun getBreathPatternStateHolder(uuid: String) {
        _breathPatternStateHolder = getBreathPatternItemByUuidUseCase(uuid)?.let { mapper.toBreathPatternStateHolder(it) }
        if(_breathPatternStateHolder != null) {
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