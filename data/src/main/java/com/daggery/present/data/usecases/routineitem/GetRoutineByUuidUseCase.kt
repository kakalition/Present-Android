package com.daggery.present.data.usecases.routineitem

import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.entities.RoutineItem
import com.daggery.present.domain.repositories.NotificationItemRepository
import com.daggery.present.domain.repositories.RoutineItemRepository
import javax.inject.Inject

class GetRoutineByUuidUseCase @Inject constructor(private val repository: RoutineItemRepository){
    suspend operator fun invoke(uuid: String): RoutineItem? {
        return repository.getRoutineByUuid(uuid)
    }
}