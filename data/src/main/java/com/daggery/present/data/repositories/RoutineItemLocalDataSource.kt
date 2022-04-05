package com.daggery.present.data.repositories

import com.daggery.present.data.db.IRoutineItemDao
import com.daggery.present.data.di.CoroutineDispatcherModule.IoDispatcher
import com.daggery.present.data.mappers.RoutineItemEntityMapper
import com.daggery.present.domain.entities.RoutineItem
import com.daggery.present.domain.repositories.RoutineItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoutineItemLocalDataSource @Inject constructor(
    private val dao: IRoutineItemDao,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val mapper: RoutineItemEntityMapper
) : RoutineItemRepository {

    override fun getRoutinesFlow(): Flow<List<RoutineItem>> {
        return dao.getRoutinesFlow().map {
            it.map { entity -> mapper.toRoutineItem(entity) }
        }
    }

    override suspend fun getRoutines(): List<RoutineItem> {
        return withContext(coroutineDispatcher) {
            return@withContext dao.getRoutines().map { mapper.toRoutineItem(it) }
        }
    }

    override suspend fun getRoutineByUuid(uuid: String): RoutineItem? {
        return withContext(coroutineDispatcher) {
            val value = dao.getRoutineByUuid(uuid)

            if (value == null) null
            else mapper.toRoutineItem(value)

        }
    }

    override suspend fun addRoutine(value: RoutineItem) {
        withContext(coroutineDispatcher) {
            dao.addRoutine(mapper.toRoutineItemEntity(value))
        }
    }

    override suspend fun updateRoutine(value: RoutineItem) {
        withContext(coroutineDispatcher) {
            dao.updateRoutine(mapper.toRoutineItemEntity(value))
        }
    }

    override suspend fun deleteRoutine(value: RoutineItem) {
        withContext(coroutineDispatcher) {
            dao.deleteRoutine(mapper.toRoutineItemEntity(value))
        }
    }
}