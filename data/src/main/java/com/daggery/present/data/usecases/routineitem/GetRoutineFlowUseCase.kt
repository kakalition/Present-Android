package com.daggery.present.data.usecases.routineitem

import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.entities.RoutineItem
import com.daggery.present.domain.repositories.NotificationItemRepository
import com.daggery.present.domain.repositories.RoutineItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRoutineFlowUseCase @Inject constructor(private val repository: RoutineItemRepository){
    operator fun invoke(): Flow<List<RoutineItem>> {
        return repository.getRoutinesFlow()
    }
}