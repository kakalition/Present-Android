package com.daggery.present.data

import com.daggery.present.domain.entities.RoutineItem
import kotlinx.coroutines.flow.Flow

interface IRoutineItemDao {

    fun getRoutinesFlow(): Flow<List<RoutineItem>>

    suspend fun getRoutines(): List<RoutineItem>

    suspend fun getRoutineByUuid(uuid: String): RoutineItem?

    suspend fun addRoutine(value: RoutineItem)

    suspend fun updateRoutine(value: RoutineItem)

    suspend fun deleteRoutine(value: RoutineItem)
}