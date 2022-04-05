package com.daggery.present.routinepage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daggery.present.data.usecases.routineitem.*
import com.daggery.present.domain.entities.RoutineItem
import com.daggery.present.routinepage.entities.RoutineState
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoutinePageViewModel @Inject constructor(
    private val getRoutinesFlowUseCase: GetRoutineFlowUseCase,
    private val getRoutineByUuidUseCase: GetRoutineByUuidUseCase,
    private val addRoutineUseCase: AddRoutineUseCase,
    private val updateRoutineUseCase: UpdateRoutineUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase
) : ViewModel() {

    private var _routineState = MutableStateFlow<RoutineState>(RoutineState.Loading)
    val routineState = _routineState.asStateFlow()

    private var collectJob: Job? = null

    fun collectState() {
        if (collectJob != null) collectJob?.cancel()
        collectJob = viewModelScope.launch {
            getRoutinesFlowUseCase()
                .catch { _routineState.emit(RoutineState.Error(it)) }
                .collect {
                    ensureActive()
                    // TODO: Find when to pause this emission
                    _routineState.emit(RoutineState.Result(it))
                }
        }
    }

    suspend fun getRoutineItem(uuid: String): RoutineItem? {
        var value: RoutineItem? = null
        val job = viewModelScope.launch {
            value = getRoutineByUuidUseCase(uuid)
        }
        job.join()
        return value
    }

    fun addRoutineItem(value: RoutineItem) {
        viewModelScope.launch {
            addRoutineUseCase(value)
        }
    }

    fun updateRoutineItem(value: RoutineItem) {
        viewModelScope.launch {
            updateRoutineUseCase(value)
        }
    }

    fun deleteRoutineItem(value: RoutineItem) {
        viewModelScope.launch {
            deleteRoutineUseCase(value)
        }
    }
}
