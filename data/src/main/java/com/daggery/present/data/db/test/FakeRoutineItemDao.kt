package com.daggery.present.data.db.test

import com.daggery.present.data.db.interfaces.IRoutineItemDao
import com.daggery.present.data.entities.RoutineItemEntity
import com.daggery.present.domain.entities.Day
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FakeRoutineItemDao : IRoutineItemDao {

    private val routineItemFlow = MutableStateFlow(
        List(3) {
            RoutineItemEntity("$it", "Routine $it", it, it, listOf(Day.SU, Day.MO), false)
        }
    )

    override fun getRoutinesFlow(): StateFlow<List<RoutineItemEntity>> {
        return routineItemFlow.asStateFlow()
    }

    override suspend fun getRoutines(): List<RoutineItemEntity> {
        return routineItemFlow.value
    }

    override suspend fun getRoutineByUuid(uuid: String): RoutineItemEntity? {
        return routineItemFlow.value.singleOrNull { it.uuid == uuid }
    }

    override suspend fun addRoutine(value: RoutineItemEntity) {
        val newValue = routineItemFlow.value.toMutableList()
        newValue.add(value)
        routineItemFlow.emit(newValue)
    }

    override suspend fun updateRoutine(value: RoutineItemEntity) {
        val newValue = routineItemFlow.value.map {
            if (it.uuid == value.uuid) value
            else it
        }
        routineItemFlow.emit(newValue)
    }

    override suspend fun deleteRoutine(value: RoutineItemEntity) {
        val newValue = routineItemFlow.value.toMutableList()
        newValue.removeIf { it.uuid == value.uuid }
        routineItemFlow.emit(newValue)
    }
}