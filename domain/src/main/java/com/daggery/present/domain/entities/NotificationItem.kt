package com.daggery.present.domain.entities

data class NotificationItem(
    val uuid: String,
    val message: String,
    val hour: Int,
    val minute: Int,
    val isActive: Boolean
)