package com.daggery.present.data.repositories

import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.repositories.BreathPatternRepository
import kotlinx.coroutines.flow.Flow

class BreathPatternLocalDataSource(

) : BreathPatternRepository {
    override suspend fun getBreathPatternItemsFlow(): Flow<List<BreathPatternItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBreathPatternItems(): List<BreathPatternItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getBreathPatternItemByUuid(uuid: String): BreathPatternItem? {
        TODO("Not yet implemented")
    }

    override suspend fun addBreathPattern(value: BreathPatternItem) {
        TODO("Not yet implemented")
    }

    override suspend fun updateBreathPattern(value: BreathPatternItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBreathPattern(value: BreathPatternItem) {
        TODO("Not yet implemented")
    }
}