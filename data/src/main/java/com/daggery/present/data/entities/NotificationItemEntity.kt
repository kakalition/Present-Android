package com.daggery.present.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_item")
data class NotificationItemEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "hour") val hour: Int,
    @ColumnInfo(name = "minute") val minute: Int,
    @ColumnInfo(name = "is_active") val isActive: Boolean
)