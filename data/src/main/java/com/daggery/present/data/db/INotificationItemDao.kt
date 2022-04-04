package com.daggery.present.data.db

import com.daggery.present.domain.entities.NotificationItem
import kotlinx.coroutines.flow.Flow

interface INotificationItemDao {

    fun getNotificationsFlow(): Flow<List<NotificationItem>>

    suspend fun getNotifications(): List<NotificationItem>

    suspend fun getNotificationByUuid(uuid: String): NotificationItem?

    suspend fun addNotification(value: NotificationItem)

    suspend fun updateNotification(value: NotificationItem)

    suspend fun deleteNotification(value: NotificationItem)
}