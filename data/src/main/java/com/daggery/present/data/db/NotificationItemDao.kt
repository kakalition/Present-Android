package com.daggery.present.data.db

import androidx.room.*
import com.daggery.present.data.entities.NotificationItemEntity
import com.daggery.present.domain.entities.NotificationItem
import kotlinx.coroutines.flow.Flow

@Dao
internal interface NotificationItemDao : INotificationItemDao {

    @Query("SELECT * FROM notification_item ORDER BY hour DESC")
    override fun getNotificationsFlow(): Flow<List<NotificationItemEntity>>

    @Query("SELECT * FROM notification_item ORDER BY hour DESC")
    override suspend fun getNotifications(): List<NotificationItemEntity>

    @Query("SELECT * FROM notification_item WHERE uuid = :uuid")
    override suspend fun getNotificationByUuid(uuid: String): NotificationItemEntity?

    @Insert
    override suspend fun addNotification(value: NotificationItemEntity)

    @Update
    override suspend fun updateNotification(value: NotificationItemEntity)

    @Delete
    override suspend fun deleteNotification(value: NotificationItemEntity)
}