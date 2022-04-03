package com.daggery.present.data.repositories

import com.daggery.present.data.db.IBreathPatternDao
import com.daggery.present.data.di.CoroutineDispatcherModule.IoDispatcher
import com.daggery.present.data.mappers.BreathPatternItemEntityMapper
import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.repositories.BreathPatternRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class BreathPatternLocalDataSource @Inject constructor(
    private val dao: IBreathPatternDao,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val mapper: BreathPatternItemEntityMapper
) : BreathPatternRepository {

    override fun getBreathPatternItemsFlow(): Flow<List<BreathPatternItem>> {
        return dao.getBreathPatternItemEntitiesFlow().map { it.map { entity -> mapper.toBreathPatternItem(entity) } }
    }

    override suspend fun getBreathPatternItems(): List<BreathPatternItem> {
        return withContext(coroutineDispatcher) {
            dao.getBreathPatternItemEntities().map { entity -> mapper.toBreathPatternItem(entity) }
        }
    }

    override suspend fun getBreathPatternItemByUuid(uuid: String): BreathPatternItem? {
        return withContext(coroutineDispatcher) {
            val value = dao.getBreathPatternItemEntityByUuid(uuid)

            if(value == null) { null }
            else { mapper.toBreathPatternItem(value) }
        }
    }

    override suspend fun addBreathPattern(value: BreathPatternItem) {
        withContext(coroutineDispatcher) {
            dao.addBreathPattern(mapper.toBreathPatternItemEntity(value))
        }
    }

    override suspend fun updateBreathPattern(value: BreathPatternItem) {
        withContext(coroutineDispatcher) {
            dao.updateBreathPattern(mapper.toBreathPatternItemEntity(value))
        }
    }

    override suspend fun deleteBreathPattern(value: BreathPatternItem) {
        withContext(coroutineDispatcher) {
            dao.deleteBreathPattern(mapper.toBreathPatternItemEntity(value))
        }
    }

}