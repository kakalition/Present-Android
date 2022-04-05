package com.daggery.present.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.daggery.present.data.IRoutineItemDao
import com.daggery.present.domain.entities.RoutineItem
import kotlinx.coroutines.flow.Flow

interface RoutineItemDao : IRoutineItemDao{

    @Query("SELECT * FROM routine_item ORDER BY hour DESC")
    override fun getRoutinesFlow(): Flow<List<RoutineItem>>

    @Query("SELECT * FROM routine_item ORDER BY hour DESC")
    override suspend fun getRoutines(): List<RoutineItem>

    @Query("SELECT * FROM routine_item WHERE uuid = :uuid")
    override suspend fun getRoutineByUuid(uuid: String): RoutineItem?

    @Insert
    override suspend fun addRoutine(value: RoutineItem)

    @Update
    override suspend fun updateRoutine(value: RoutineItem)

    @Delete
    override suspend fun deleteRoutine(value: RoutineItem)
}