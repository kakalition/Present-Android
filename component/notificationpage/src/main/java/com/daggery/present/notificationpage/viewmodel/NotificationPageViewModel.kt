package com.daggery.present.notificationpage.viewmodel

import androidx.lifecycle.ViewModel
import com.daggery.present.data.usecases.notificationitem.AddNotificationUseCase
import com.daggery.present.data.usecases.notificationitem.GetNotificationByUuidUseCase
import com.daggery.present.data.usecases.notificationitem.GetNotificationsFlowUseCase
import com.daggery.present.data.usecases.notificationitem.UpdateNotificationUseCase
import javax.inject.Inject

class NotificationPageViewModel @Inject constructor(
    private val getNotificationsFlowUseCase: GetNotificationsFlowUseCase,
    private val getNotificationByUuidUseCase: GetNotificationByUuidUseCase,
    private val addNotificationUseCase: AddNotificationUseCase,
    private val updateNotificationUseCase: UpdateNotificationUseCase,
    private val deleteNotificationUseCase: AddNotificationUseCase
) : ViewModel(){

}
