package com.daggery.present.breathpage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.breathpage.entities.BreathStateEnum
import com.daggery.present.breathpage.entities.TimerState
import com.daggery.present.breathpage.entities.TimerStatePairBuilder
import com.daggery.present.breathpage.mappers.BreathPatternStateHolderMapper
import com.daggery.present.data.usecases.GetBreathPatternItemByUuidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreathPageViewModel @Inject constructor(
    private val timerStatePairBuilder: TimerStatePairBuilder,
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
            _timerStateFlow = timerStateFlowBuilder(buildTimerStatePairList())
            viewModelScope.launch {
                _timerStateFlow.collect {
                    _timerState.emit(it)
                }
            }
        }
    }

    private fun buildTimerStatePairList(): List<Pair<TimerState, TimerState>> {
        val mutableList = mutableListOf<Pair<TimerState, TimerState>>()

        with(breathPatternStateHolder) {

            mutableList.add(
                timerStatePairBuilder.setFirstDuration(3)
                    .setFirstState(BreathStateEnum.READY)
                    .setSecondDuration(inhaleDuration)
                    .setSecondState(BreathStateEnum.INHALE)
                    .build()
            )

            repeat(breathPatternStateHolder.repetitions) {
                if(holdPostInhaleDuration != 0) {
                    mutableList.add(
                        Pair(
                            TimerState(inhaleDuration, BreathStateEnum.INHALE),
                            TimerState(holdPostInhaleDuration, BreathStateEnum.HOLD_POST_INHALE)
                        )
                    )
                    mutableList.add(
                        Pair(
                            TimerState(holdPostInhaleDuration, BreathStateEnum.HOLD_POST_INHALE),
                            TimerState(exhaleDuration, BreathStateEnum.EXHALE)
                        )
                    )
                } else {
                    mutableList.add(
                        Pair(
                            TimerState(inhaleDuration, BreathStateEnum.INHALE),
                            TimerState(exhaleDuration, BreathStateEnum.EXHALE)
                        )
                    )
                }

                if(holdPostExhaleDuration != 0) {
                    mutableList.add(
                        Pair(
                            TimerState(exhaleDuration, BreathStateEnum.EXHALE),
                            TimerState(holdPostExhaleDuration, BreathStateEnum.HOLD_POST_EXHALE)
                        )
                    )
                    if(it == breathPatternStateHolder.repetitions - 1) {
                        mutableList.add(
                            Pair(
                                TimerState(holdPostExhaleDuration, BreathStateEnum.HOLD_POST_EXHALE),
                                TimerState(0, BreathStateEnum.FINISHED)
                            )
                        )
                    } else {
                        mutableList.add(
                            Pair(
                                TimerState(holdPostExhaleDuration, BreathStateEnum.HOLD_POST_EXHALE),
                                TimerState(inhaleDuration, BreathStateEnum.INHALE)
                            )
                        )
                    }
                } else {
                    if (it == breathPatternStateHolder.repetitions - 1) {
                        mutableList.add(
                            Pair(
                                TimerState(exhaleDuration, BreathStateEnum.EXHALE),
                                TimerState(0, BreathStateEnum.FINISHED)
                            )
                        )
                    } else {
                        mutableList.add(
                            Pair(
                                TimerState(exhaleDuration, BreathStateEnum.EXHALE),
                                TimerState(inhaleDuration, BreathStateEnum.INHALE)
                            )
                        )
                    }
                }
            }
        }

        mutableList.add(
            Pair(
                TimerState(0, BreathStateEnum.FINISHED),
                TimerState(0, BreathStateEnum.FINISHED)
            )
        )

        return mutableList.toList()
    }

    private fun buildTimerStateList(): List<TimerState> {
        val mutableList = mutableListOf<TimerState>()

        //mutableList.add(TimerState(1, BreathStateEnum.GROUND))
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

        return mutableList.filterNot { it.duration == 0 }
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