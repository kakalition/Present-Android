package com.daggery.present.breathpage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.breathpage.entities.BreathStateEnum
import com.daggery.present.breathpage.helper.TimerState
import com.daggery.present.breathpage.helper.TimerStatePairBuilder
import com.daggery.present.breathpage.helper.TimerStatePairListBuilder
import com.daggery.present.breathpage.mappers.BreathPatternStateHolderMapper
import com.daggery.present.data.usecases.GetBreathPatternItemByUuidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreathPageViewModel @Inject constructor(
    private val timerStatePairListBuilder: TimerStatePairListBuilder,
    private val mapper: BreathPatternStateHolderMapper,
    private val getBreathPatternItemByUuidUseCase: GetBreathPatternItemByUuidUseCase
): ViewModel() {

    private var _breathPatternStateHolder: BreathPatternStateHolder? = null
    val breathPatternStateHolder get() = BreathPatternStateHolder("0", "Box Breathing", 1, 2, 1, 2, 1, 2)

    private lateinit var _timerStateFlow: Flow<Pair<TimerState, TimerState>>
    private var _timerState = MutableStateFlow(Pair(
        TimerState(0, BreathStateEnum.GROUND, "Start"),
        TimerState(3, BreathStateEnum.READY, "Ready (3)")
    ))
    val timerState get() = _timerState.asStateFlow()

    private var _isSessionPaused = MutableStateFlow(true)
    val isSessionPaused get() = _isSessionPaused.asStateFlow()
    private var isAnimationRunning = MutableStateFlow(false)

    // TODO: Implement
    suspend fun resetSession() {
        //_timerStateFlow = timerStateFlowBuilder(buildTimerStateList())
    }

    fun stopSession() {
        viewModelScope.launch {
            _isSessionPaused.emit(true)
        }
    }
    
    fun startSession() {
        viewModelScope.launch {
            _isSessionPaused.emit(false)
        }
    }

    fun changeRunningAnimationState(value: Boolean) {
        viewModelScope.launch {
            isAnimationRunning.emit(value)
        }
    }

    suspend fun getBreathPatternStateHolder(uuid: String) {
        _breathPatternStateHolder = getBreathPatternItemByUuidUseCase(uuid)?.let { mapper.toBreathPatternStateHolder(it) }
        if (breathPatternStateHolder != null) {
            val listValue= timerStatePairListBuilder
                .setStateHolder(breathPatternStateHolder)
                .build()
            _timerStateFlow = timerStateFlowBuilder(listValue)
            viewModelScope.launch {
                _timerStateFlow.collect {
                    _timerState.emit(it)
                }
            }
        }
    }

    private suspend fun timerStateFlowBuilder(value: List<Pair<TimerState, TimerState>>): Flow<Pair<TimerState, TimerState>> {
        return flow {
            value.forEach {
                // If session is paused, wait until not paused
                if(isSessionPaused.value) isSessionPaused.first { isPaused -> !isPaused }
                // If animation still running, wait until it is finished
                if(isAnimationRunning.value) isAnimationRunning.first { isRun -> !isRun }
                emit(it)
                delay(it.first.duration * 1000L)
            }
        }
    }
}