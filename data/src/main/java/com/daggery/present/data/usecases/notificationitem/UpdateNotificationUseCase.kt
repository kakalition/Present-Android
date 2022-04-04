package com.daggery.present.data.usecases.notificationitem

import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.repositories.NotificationItemRepository
import javax.inject.Inject

class UpdateNotificationUseCase @Inject constructor(private val repository: NotificationItemRepository){
    suspend operator fun invoke(value: NotificationItem) {
        return repository.updateNotification(value)
    }
}