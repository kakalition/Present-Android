package com.daggery.present.data.db

import androidx.room.*
import com.daggery.present.domain.entities.BreathPatternItem
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BreathPatternDao : IBreathPatternDao {

    @Query("SELECT * FROM breath_pattern ORDER BY name DESC")
    override fun getBreathPatternItemsFlow(): Flow<List<BreathPatternItem>>

    @Query("SELECT * FROM breath_pattern ORDER BY name DESC")
    override suspend fun getBreathPatternItems(): List<BreathPatternItem>

    @Query("SELECT * FROM breath_pattern WHERE name = :uuid")
    override suspend fun getBreathPatternItemByUuid(uuid: String): BreathPatternItem?

    @Insert
    override suspend fun addBreathPattern(value: BreathPatternItem)

    @Update
    override suspend fun updateBreathPattern(value: BreathPatternItem)

    @Delete
    override suspend fun deleteBreathPattern(value: BreathPatternItem)
}