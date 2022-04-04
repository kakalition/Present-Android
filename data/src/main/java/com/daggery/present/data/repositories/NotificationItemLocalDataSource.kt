package com.daggery.present.data.repositories

import com.daggery.present.data.db.NotificationItemDao
import com.daggery.present.data.di.CoroutineDispatcherModule.IoDispatcher
import com.daggery.present.data.mappers.NotificationItemEntityMapper
import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.repositories.NotificationItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class NotificationItemLocalDataSource @Inject constructor(
    private val dao: NotificationItemDao,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val mapper: NotificationItemEntityMapper
) : NotificationItemRepository{

    override fun getNotificationsFlow(): Flow<List<NotificationItem>> {
        return dao.getNotificationsFlow().map {
            it.map { entity -> mapper.toNotificationItem(entity) }
        }
    }

    override suspend fun getNotifications(): List<NotificationItem> {
        return withContext(coroutineDispatcher) {
            return@withContext dao.getNotifications().map { entity -> mapper.toNotificationItem(entity) }
        }
    }

    override suspend fun getNotificationByUuid(uuid: String): NotificationItem? {
        return withContext(coroutineDispatcher) {
            val value = dao.getNotificationByUuid(uuid)

            if(value == null) { null }
            else { mapper.toNotificationItem(value) }
        }
    }

    override suspend fun addNotification(value: NotificationItem) {
        withContext(coroutineDispatcher) {
            dao.addNotification(mapper.toNotificationItemEntity(value))
        }
    }

    override suspend fun updateNotification(value: NotificationItem) {
        withContext(coroutineDispatcher) {
            dao.updateNotification(mapper.toNotificationItemEntity(value))
        }
    }

    override suspend fun deleteNotification(value: NotificationItem) {
        withContext(coroutineDispatcher) {
            dao.deleteNotification(mapper.toNotificationItemEntity(value))
        }
    }
}