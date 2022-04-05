package com.daggery.present.routinepage.entities

import com.daggery.present.domain.entities.RoutineItem

sealed class RoutinesState {
    object Loading : RoutinesState()
    data class Result(val value: List<RoutineItem>) : RoutinesState()
    data class Error(val error: Throwable) : RoutinesState()
}