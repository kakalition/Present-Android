package com.daggery.present.data.db.test

import com.daggery.present.data.db.INotificationItemDao
import com.daggery.present.data.entities.NotificationItemEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeNotificationItemDao : INotificationItemDao {

    private val notificationItemFlow = MutableStateFlow(
        List(3) {
            NotificationItemEntity("$it", "Notification $it", it, it, false)
        }
    )

    override fun getNotificationsFlow(): StateFlow<List<NotificationItemEntity>> {
        return notificationItemFlow.asStateFlow()
    }

    override suspend fun getNotifications(): List<NotificationItemEntity> {
        return notificationItemFlow.value
    }

    override suspend fun getNotificationByUuid(uuid: String): NotificationItemEntity? {
        return notificationItemFlow.value.singleOrNull { it.uuid == uuid }
    }

    override suspend fun addNotification(value: NotificationItemEntity) {
        val newValue = notificationItemFlow.value.toMutableList()
        newValue.add(value)
        notificationItemFlow.emit(newValue)
    }

    override suspend fun updateNotification(value: NotificationItemEntity) {
        val newValue = notificationItemFlow.value.map {
            if(it.uuid == value.uuid) { value }
            else it
        }
        notificationItemFlow.emit(newValue)
    }

    override suspend fun deleteNotification(value: NotificationItemEntity) {
        val newValue = notificationItemFlow.value.toMutableList()
        newValue.removeIf { it.uuid == value.uuid }
        notificationItemFlow.emit(newValue)
    }
}