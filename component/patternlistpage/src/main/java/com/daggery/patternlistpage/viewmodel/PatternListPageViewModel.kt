package com.daggery.patternlistpage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daggery.patternlistpage.entities.PatternListState
import com.daggery.present.data.usecases.breathpattern.*
import com.daggery.present.domain.entities.BreathPatternItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class PatternListPageViewModel @Inject constructor(
    private val getBreathPatternItemsFlowUseCase: GetBreathPatternItemsFlowUseCase,
    private val getBreathPatternItemByUuidUseCase: GetBreathPatternItemByUuidUseCase,
    private val addBreathPatternUseCase: AddBreathPatternUseCase,
    private val updateBreathPatternUseCase: UpdateBreathPatternUseCase,
    private val deleteBreathPatternUseCase: DeleteBreathPatternUseCase,
) : ViewModel() {

    private var _patternListState = MutableStateFlow<PatternListState>(PatternListState.Loading)
    val patternListState get() = _patternListState.asStateFlow()

/*
    // Testing
    private var _patternListState = MutableStateFlow<PatternListState>(
        PatternListState.Result(
            listOf(
                BreathPatternItem(
                    "1", "Box", 1, 1, 1, 1, 1, 1
                )
            )
        )
    )
    val patternListState: StateFlow<PatternListState> = _patternListState.asStateFlow()
*/

    private var _isOffScreen = MutableStateFlow(true)
    private val isOffScreen get() = _isOffScreen.asStateFlow()

    private var collectJob: Job? = null

    fun changeScreenState(value: Boolean) {
        viewModelScope.launch {
            _isOffScreen.emit(value)
        }
    }

    suspend fun getPattern(uuid: String): BreathPatternItem? {
        var value: BreathPatternItem? = null
        val job = viewModelScope.async { value = getBreathPatternItemByUuidUseCase(uuid) }
        job.join()
        return value
    }

    fun updatePattern(value: BreathPatternItem) {
        viewModelScope.launch {
            updateBreathPatternUseCase(value)
        }
    }

    fun deletePattern(value: BreathPatternItem) {
        viewModelScope.launch {
            deleteBreathPatternUseCase(value)
        }
    }

    fun collectState() {
        if (collectJob != null) collectJob?.cancel()
        collectJob = viewModelScope.launch {
            getBreathPatternItemsFlowUseCase().collect {
                ensureActive()
                if (isOffScreen.value) isOffScreen.first { isOffScreen -> !isOffScreen }
                _patternListState.emit(PatternListState.Result(it))
            }
        }
    }
}