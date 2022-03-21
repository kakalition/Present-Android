package com.daggery.present.data.repositories

import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.repositories.BreathPatternRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class BreathPatternRepositoryImpl @Inject constructor(
    private val localDataSource: BreathPatternLocalDataSource
) : BreathPatternRepository {
    override fun getBreathPatternItemsFlow(): Flow<List<BreathPatternItem>> {
        return localDataSource.getBreathPatternItemsFlow()
    }

    override suspend fun getBreathPatternItems(): List<BreathPatternItem> {
        return localDataSource.getBreathPatternItems()
    }

    override suspend fun getBreathPatternItemByUuid(uuid: String): BreathPatternItem? {
        return localDataSource.getBreathPatternItemByUuid(uuid)
    }

    override suspend fun addBreathPattern(value: BreathPatternItem) {
        localDataSource.addBreathPattern(value)
    }

    override suspend fun updateBreathPattern(value: BreathPatternItem) {
        localDataSource.updateBreathPattern(value)
    }

    override suspend fun deleteBreathPattern(value: BreathPatternItem) {
        localDataSource.deleteBreathPattern(value)
    }
}