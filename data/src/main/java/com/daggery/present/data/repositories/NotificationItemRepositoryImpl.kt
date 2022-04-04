package com.daggery.present.data.repositories

import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.repositories.NotificationItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class NotificationItemRepositoryImpl @Inject constructor(
    private val localDataSource: NotificationItemLocalDataSource
): NotificationItemRepository {

    override fun getNotificationsFlow(): Flow<List<NotificationItem>> {
        return localDataSource.getNotificationsFlow()
    }

    override suspend fun getNotifications(): List<NotificationItem> {
        return localDataSource.getNotifications()
    }

    override suspend fun getNotificationByUuid(uuid: String): NotificationItem? {
        return localDataSource.getNotificationByUuid(uuid)
    }

    override suspend fun addNotification(value: NotificationItem) {
        localDataSource.addNotification(value)
    }

    override suspend fun updateNotification(value: NotificationItem) {
        localDataSource.updateNotification(value)
    }

    override suspend fun deleteNotification(value: NotificationItem) {
        localDataSource.deleteNotification(value)
    }
}