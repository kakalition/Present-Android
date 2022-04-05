package com.daggery.present.routinepage.entities

import com.daggery.present.domain.entities.RoutineItem

sealed class RoutineState {
    object Loading : RoutineState()
    data class Result(val value: List<RoutineItem>) : RoutineState()
    data class Error(val error: Throwable) : RoutineState()
}