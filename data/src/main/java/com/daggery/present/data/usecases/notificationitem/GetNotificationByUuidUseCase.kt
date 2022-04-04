package com.daggery.present.data.usecases.notificationitem

import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.repositories.NotificationItemRepository
import javax.inject.Inject

class GetNotificationByUuidUseCase @Inject constructor(private val repository: NotificationItemRepository){
    suspend operator fun invoke(uuid: String): NotificationItem? {
        return repository.getNotificationByUuid(uuid)
    }
}