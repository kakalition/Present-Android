package com.daggery.present.data.db

import com.daggery.present.data.entities.RoutineItemEntity
import com.daggery.present.domain.entities.RoutineItem
import kotlinx.coroutines.flow.Flow

interface IRoutineItemDao {

    fun getRoutinesFlow(): Flow<List<RoutineItemEntity>>

    suspend fun getRoutines(): List<RoutineItemEntity>

    suspend fun getRoutineByUuid(uuid: String): RoutineItemEntity?

    suspend fun addRoutine(value: RoutineItemEntity)

    suspend fun updateRoutine(value: RoutineItemEntity)

    suspend fun deleteRoutine(value: RoutineItemEntity)
}