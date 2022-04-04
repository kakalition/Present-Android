package com.daggery.present.data.repositories.test

import com.daggery.present.data.entities.NotificationItemEntity
import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.repositories.NotificationItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeNotificationItemRepository : NotificationItemRepository {

    private val notificationItemFlow = MutableStateFlow(
        List(3) {
            NotificationItem("$it", "Notification $it", it, it, false)
        }
    )

    override fun getNotificationsFlow(): StateFlow<List<NotificationItem>> {
        return notificationItemFlow.asStateFlow()
    }

    override suspend fun getNotifications(): List<NotificationItem> {
        return notificationItemFlow.value
    }

    override suspend fun getNotificationByUuid(uuid: String): NotificationItem? {
        return notificationItemFlow.value.singleOrNull { it.uuid == uuid }
    }

    override suspend fun addNotification(value: NotificationItem) {
        val newValue = notificationItemFlow.value.toMutableList()
        newValue.add(value)
        notificationItemFlow.emit(newValue)
    }

    override suspend fun updateNotification(value: NotificationItem) {
        val newValue = notificationItemFlow.value.map {
            if(it.uuid == value.uuid) { value }
            else it
        }
        notificationItemFlow.emit(newValue)
    }

    override suspend fun deleteNotification(value: NotificationItem) {
        val newValue = notificationItemFlow.value.toMutableList()
        newValue.removeIf { it.uuid == value.uuid }
        notificationItemFlow.emit(newValue)
    }
}