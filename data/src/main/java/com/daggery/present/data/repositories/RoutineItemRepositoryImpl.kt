package com.daggery.present.data.repositories

import com.daggery.present.domain.entities.RoutineItem
import com.daggery.present.domain.repositories.RoutineItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RoutineItemRepositoryImpl @Inject constructor(
    private val localDataSource: RoutineItemLocalDataSource
) : RoutineItemRepository {

    override fun getRoutinesFlow(): Flow<List<RoutineItem>> {
        return localDataSource.getRoutinesFlow()
    }

    override suspend fun getRoutines(): List<RoutineItem> {
        return localDataSource.getRoutines()
    }

    override suspend fun getRoutineByUuid(uuid: String): RoutineItem? {
        return localDataSource.getRoutineByUuid(uuid)
    }

    override suspend fun addRoutine(value: RoutineItem) {
        return localDataSource.addRoutine(value)
    }

    override suspend fun updateRoutine(value: RoutineItem) {
        return localDataSource.updateRoutine(value)
    }

    override suspend fun deleteRoutine(value: RoutineItem) {
        return localDataSource.deleteRoutine(value)
    }
}