package com.daggery.present.data.mappers

import com.daggery.present.data.entities.NotificationItemEntity
import com.daggery.present.domain.entities.NotificationItem

internal class NotificationItemEntityMapper {
    fun toNotificationItemEntity(value: NotificationItem): NotificationItemEntity {
        return NotificationItemEntity(
            value.uuid, value.message, value.hour, value.minute, value.isActive
        )
    }

    fun toNotificationItem(value: NotificationItemEntity): NotificationItem {
        return NotificationItem(
            value.uuid, value.message, value.hour, value.minute, value.isActive
        )
    }
}