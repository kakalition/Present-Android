package com.daggery.present.domain.repositories

import kotlinx.coroutines.flow.Flow
import com.daggery.present.domain.entities.BreathPatternItem

interface BreathPatternRepository {

    fun getBreathPatternItemsFlow(): Flow<List<BreathPatternItem>>

    suspend fun getBreathPatternItems(): List<BreathPatternItem>

    suspend fun getBreathPatternItemByUuid(uuid: String): BreathPatternItem?

    suspend fun addBreathPattern(value: BreathPatternItem)

    suspend fun updateBreathPattern(value: BreathPatternItem)

    suspend fun deleteBreathPattern(value: BreathPatternItem)
}