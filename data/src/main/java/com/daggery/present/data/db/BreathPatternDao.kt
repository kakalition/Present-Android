package com.daggery.present.data.db

import androidx.room.*
import com.daggery.present.data.entities.BreathPatternItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BreathPatternDao : IBreathPatternDao {

    @Query("SELECT * FROM breath_pattern ORDER BY name DESC")
    override fun getBreathPatternItemEntitiesFlow(): Flow<List<BreathPatternItemEntity>>

    @Query("SELECT * FROM breath_pattern ORDER BY name DESC")
    override suspend fun getBreathPatternItemEntities(): List<BreathPatternItemEntity>

    @Query("SELECT * FROM breath_pattern WHERE name = :uuid")
    override suspend fun getBreathPatternItemEntityByUuid(uuid: String): BreathPatternItemEntity?

    @Insert
    override suspend fun addBreathPattern(value: BreathPatternItemEntity)

    @Update
    override suspend fun updateBreathPattern(value: BreathPatternItemEntity)

    @Delete
    override suspend fun deleteBreathPattern(value: BreathPatternItemEntity)
}