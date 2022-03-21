package com.daggery.present.data.db

import com.daggery.present.data.entities.BreathPatternItemEntity
import kotlinx.coroutines.flow.Flow

internal interface IBreathPatternDao {

    fun getBreathPatternItemEntitiesFlow(): Flow<List<BreathPatternItemEntity>>

    suspend fun getBreathPatternItemEntities(): List<BreathPatternItemEntity>

    suspend fun getBreathPatternItemEntityByUuid(uuid: String): BreathPatternItemEntity?

    suspend fun addBreathPattern(value: BreathPatternItemEntity)

    suspend fun updateBreathPattern(value: BreathPatternItemEntity)

    suspend fun deleteBreathPattern(value: BreathPatternItemEntity)
}