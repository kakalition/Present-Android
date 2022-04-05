package com.daggery.present.notificationpage.entities

import com.daggery.present.domain.entities.NotificationItem

sealed class NotificationsState {
    object Loading : NotificationsState()
    data class Result(val value: List<NotificationItem>) : NotificationsState()
    data class Error(val error: Throwable) : NotificationsState()
}