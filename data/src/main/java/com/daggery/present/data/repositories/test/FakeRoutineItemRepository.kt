package com.daggery.present.data.repositories.test

import com.daggery.present.domain.entities.Day
import com.daggery.present.domain.entities.RoutineItem
import com.daggery.present.domain.repositories.RoutineItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeRoutineItemRepository : RoutineItemRepository {

    private val routineItemFlow = MutableStateFlow(
        List(3) {
            RoutineItem("$it", "Routine $it", it, it, listOf(Day.SU, Day.MO), false)
        }
    )

    override fun getRoutinesFlow(): StateFlow<List<RoutineItem>> {
        return routineItemFlow.asStateFlow()
    }

    override suspend fun getRoutines(): List<RoutineItem> {
        return routineItemFlow.value
    }

    override suspend fun getRoutineByUuid(uuid: String): RoutineItem? {
        return routineItemFlow.value.singleOrNull { it.uuid == uuid }
    }

    override suspend fun addRoutine(value: RoutineItem) {
        val newValue = routineItemFlow.value.toMutableList()
        newValue.add(value)
        routineItemFlow.emit(newValue)
    }

    override suspend fun updateRoutine(value: RoutineItem) {
        val newValue = routineItemFlow.value.map {
            if (it.uuid == value.uuid) value
            else it
        }
        routineItemFlow.emit(newValue)
    }

    override suspend fun deleteRoutine(value: RoutineItem) {
        val newValue = routineItemFlow.value.toMutableList()
        newValue.removeIf { it.uuid == value.uuid }
        routineItemFlow.emit(newValue)
    }
}