package com.daggery.present.data.db

import com.daggery.present.domain.entities.BreathPatternItem
import kotlinx.coroutines.flow.Flow

internal interface IBreathPatternDao {

    fun getBreathPatternItemsFlow(): Flow<List<BreathPatternItem>>

    suspend fun getBreathPatternItems(): List<BreathPatternItem>

    suspend fun getBreathPatternItemByUuid(uuid: String): BreathPatternItem?

    suspend fun addBreathPattern(value: BreathPatternItem)

    suspend fun updateBreathPattern(value: BreathPatternItem)

    suspend fun deleteBreathPattern(value: BreathPatternItem)
}