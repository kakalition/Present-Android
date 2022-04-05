package com.daggery.present.notificationpage.viewmodel

import com.daggery.present.data.repositories.test.FakeNotificationItemRepository
import com.daggery.present.data.usecases.notificationitem.AddNotificationUseCase
import com.daggery.present.data.usecases.notificationitem.GetNotificationByUuidUseCase
import com.daggery.present.data.usecases.notificationitem.GetNotificationsFlowUseCase
import com.daggery.present.data.usecases.notificationitem.UpdateNotificationUseCase
import com.daggery.present.domain.repositories.NotificationItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.specification.describe

@ExperimentalCoroutinesApi
class NotificationPageViewModelTest : Spek({

    Feature("NotificationPage ViewModel Test") {

        lateinit var testCoroutineScheduler: TestCoroutineScheduler
        lateinit var coroutineDispatcher: TestDispatcher

        lateinit var repository: NotificationItemRepository
        lateinit var getNotificationsFlowUseCase: GetNotificationsFlowUseCase
        lateinit var getNotificationByUuidUseCase: GetNotificationByUuidUseCase
        lateinit var addNotificationUseCase: AddNotificationUseCase
        lateinit var updateNotificationUseCase: UpdateNotificationUseCase
        lateinit var deleteNotificationUseCase: AddNotificationUseCase

        lateinit var sut: NotificationPageViewModel

        beforeEachScenario {
            testCoroutineScheduler = TestCoroutineScheduler()
            coroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)

            repository = FakeNotificationItemRepository()
            getNotificationsFlowUseCase = GetNotificationsFlowUseCase(repository)
            getNotificationByUuidUseCase = GetNotificationByUuidUseCase(repository)
            addNotificationUseCase = AddNotificationUseCase(repository)
            updateNotificationUseCase = UpdateNotificationUseCase(repository)
            deleteNotificationUseCase = AddNotificationUseCase(repository)
            sut = NotificationPageViewModel(
                getNotificationsFlowUseCase,
                getNotificationByUuidUseCase,
                addNotificationUseCase,
                updateNotificationUseCase,
                deleteNotificationUseCase
            )

            Dispatchers.setMain(coroutineDispatcher)
        }

        afterEachScenario {
            Dispatchers.resetMain()
        }

        Scenario("collecting notificationItem notificationsFlow") {

        }
    }
})
