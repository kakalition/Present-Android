package com.daggery.present.data.usecases.notificationitem

import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.repositories.NotificationItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsFlowUseCase @Inject constructor(private val repository: NotificationItemRepository){
    operator fun invoke(): Flow<List<NotificationItem>> {
        return repository.getNotificationsFlow()
    }
}