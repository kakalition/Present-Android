package com.daggery.present.notificationpage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daggery.present.data.usecases.notificationitem.AddNotificationUseCase
import com.daggery.present.data.usecases.notificationitem.GetNotificationByUuidUseCase
import com.daggery.present.data.usecases.notificationitem.GetNotificationsFlowUseCase
import com.daggery.present.data.usecases.notificationitem.UpdateNotificationUseCase
import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.notificationpage.entities.NotificationsState
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationPageViewModel @Inject constructor(
    private val getNotificationsFlowUseCase: GetNotificationsFlowUseCase,
    private val getNotificationByUuidUseCase: GetNotificationByUuidUseCase,
    private val addNotificationUseCase: AddNotificationUseCase,
    private val updateNotificationUseCase: UpdateNotificationUseCase,
    private val deleteNotificationUseCase: AddNotificationUseCase
) : ViewModel(){

    private var collectJob: Job? = null

    private val _notificationsState = MutableStateFlow<NotificationsState>(NotificationsState.Loading)
    val notificationState = _notificationsState.asStateFlow()

    fun collectState() {
        if (collectJob != null) collectJob?.cancel()
        collectJob = viewModelScope.launch {
            getNotificationsFlowUseCase()
                .catch { _notificationsState.emit(NotificationsState.Error(it)) }
                .collect {
                    ensureActive()
                    // TODO: Find when to pause this emission
                    _notificationsState.emit(NotificationsState.Result(it))
                }
        }
    }

    suspend fun getNotificationItem(uuid: String): NotificationItem? {
        var value: NotificationItem? = null
        val job = viewModelScope.launch {
            value = getNotificationByUuidUseCase(uuid)
        }
        job.join()
        return value
    }

    fun addNotificationItem(value: NotificationItem) {
        viewModelScope.launch {
            addNotificationUseCase(value)
        }
    }

    fun updateNotificationItem(value: NotificationItem) {
        viewModelScope.launch {
            updateNotificationUseCase(value)
        }
    }

    fun deleteNotificationItem(value: NotificationItem) {
        viewModelScope.launch {
            deleteNotificationUseCase(value)
        }
    }
}