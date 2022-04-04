package com.daggery.present.data.db

import com.daggery.present.data.entities.NotificationItemEntity
import com.daggery.present.domain.entities.NotificationItem
import kotlinx.coroutines.flow.Flow

internal interface INotificationItemDao {

    fun getNotificationsFlow(): Flow<List<NotificationItemEntity>>

    suspend fun getNotifications(): List<NotificationItemEntity>

    suspend fun getNotificationByUuid(uuid: String): NotificationItemEntity?

    suspend fun addNotification(value: NotificationItemEntity)

    suspend fun updateNotification(value: NotificationItemEntity)

    suspend fun deleteNotification(value: NotificationItemEntity)
}