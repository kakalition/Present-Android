package com.daggery.present.data.db

import androidx.room.*
import com.daggery.present.domain.entities.NotificationItem
import kotlinx.coroutines.flow.Flow

@Dao
internal interface NotificationItemDao : INotificationItemDao {

    @Query("SELECT * FROM notification_item ORDER BY hour DESC")
    override fun getNotificationsFlow(): Flow<List<NotificationItem>>

    @Query("SELECT * FROM notification_item ORDER BY hour DESC")
    override suspend fun getNotifications(): List<NotificationItem>

    @Query("SELECT * FROM notification_item WHERE uuid = :uuid")
    override suspend fun getNotificationByUuid(uuid: String): NotificationItem?

    @Insert
    override suspend fun addNotification(value: NotificationItem)

    @Update
    override suspend fun updateNotification(value: NotificationItem)

    @Delete
    override suspend fun deleteNotification(value: NotificationItem)
}