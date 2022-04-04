package com.daggery.present.domain.entities

data class NotificationItem(
    val message: String,
    val hour: Int,
    val minute: Int,
    val isActive: Boolean
)