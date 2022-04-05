package com.daggery.present.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.daggery.present.data.entities.RoutineItemEntity
import kotlinx.coroutines.flow.Flow

interface RoutineItemDao : IRoutineItemDao {

    @Query("SELECT * FROM routine_item ORDER BY hour DESC")
    override fun getRoutinesFlow(): Flow<List<RoutineItemEntity>>

    @Query("SELECT * FROM routine_item ORDER BY hour DESC")
    override suspend fun getRoutines(): List<RoutineItemEntity>

    @Query("SELECT * FROM routine_item WHERE uuid = :uuid")
    override suspend fun getRoutineByUuid(uuid: String): RoutineItemEntity?

    @Insert
    override suspend fun addRoutine(value: RoutineItemEntity)

    @Update
    override suspend fun updateRoutine(value: RoutineItemEntity)

    @Delete
    override suspend fun deleteRoutine(value: RoutineItemEntity)
}