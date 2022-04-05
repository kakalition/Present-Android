package com.daggery.present.routinepage.viewmodel

import com.daggery.present.data.repositories.test.FakeRoutineItemRepository
import com.daggery.present.data.usecases.routineitem.*
import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.repositories.RoutineItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

@ExperimentalCoroutinesApi
class RoutinePageViewModelTest : Spek({

    Feature("RoutinePage ViewModel Test") {

        lateinit var testCoroutineScheduler: TestCoroutineScheduler
        lateinit var coroutineDispatcher: TestDispatcher

        lateinit var repository: RoutineItemRepository
        lateinit var getRoutinesFlowUseCase: GetRoutineFlowUseCase
        lateinit var getRoutineByUuidUseCase: GetRoutineByUuidUseCase
        lateinit var addRoutineUseCase: AddRoutineUseCase
        lateinit var updateRoutineUseCase: UpdateRoutineUseCase
        lateinit var deleteRoutineUseCase: DeleteRoutineUseCase

        lateinit var sut: RoutinePageViewModel

        beforeEachScenario {
            testCoroutineScheduler = TestCoroutineScheduler()
            coroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)

            repository = FakeRoutineItemRepository()
            getRoutinesFlowUseCase = GetRoutineFlowUseCase(repository)
            getRoutineByUuidUseCase = GetRoutineByUuidUseCase(repository)
            addRoutineUseCase = AddRoutineUseCase(repository)
            updateRoutineUseCase = UpdateRoutineUseCase(repository)
            deleteRoutineUseCase = DeleteRoutineUseCase(repository)
            sut = RoutinePageViewModel(
                getRoutinesFlowUseCase,
                getRoutineByUuidUseCase,
                addRoutineUseCase,
                updateRoutineUseCase,
                deleteRoutineUseCase
            )

            Dispatchers.setMain(coroutineDispatcher)
        }

        afterEachScenario {
            Dispatchers.resetMain()
        }

        Scenario("collecting notificationsState") {

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(NotificationsState.Loading, sut.notificationState.value)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            Then("collected value is emitted to notificationsState") {
                runTest {
                    Assertions.assertInstanceOf(NotificationsState.Result::class.java, sut.notificationState.value)
                }
            }
        }

        Scenario("getting NotificationItem") {
            var value: NotificationItem? = null

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(NotificationsState.Loading, sut.notificationState.value)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            And("calling getNotificationItem with given id") {
                runTest {
                    val uuidOne = "1"
                    value = sut.getNotificationItem(uuidOne)
                }
            }

            Then("it should return NotificationItem with corresponding uuid") {
                runTest {
                    val expected = NotificationItem("1", "Notification 1", 1, 1, false)
                    Assertions.assertEquals(expected, value)
                }
            }
        }

        Scenario("adding NotificationItem") {
            val valueThree = NotificationItem(
                "3", "Notification 3", 1, 1, false
            )

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(NotificationsState.Loading, sut.notificationState.value)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            And("calling addNotificationItem with given NotificationItem") {
                runTest {
                    sut.addNotificationItem(valueThree)
                }
            }

            Then("given NotificationItem should be in database") {
                runTest {
                    val value = sut.getNotificationItem("3")
                    Assertions.assertEquals(valueThree, value)
                }
            }
        }

        Scenario("updating NotificationItem") {
            val updatedValue = NotificationItem(
                "2", "Updated Notification 2", 1, 1, false
            )

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(NotificationsState.Loading, sut.notificationState.value)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            And("calling updateNotificationItem with given NotificationItem") {
                runTest {
                    sut.updateNotificationItem(updatedValue)
                }
            }

            Then("NotificationItem with corresponding uuid should be in updated") {
                runTest {
                    val value = sut.getNotificationItem("2")
                    Assertions.assertEquals(updatedValue, value)
                }
            }
        }

        Scenario("deleting NotificationItem") {
            val uuidOne = "1"
            var value: NotificationItem? = null

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(NotificationsState.Loading, sut.notificationState.value)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            And("calling deleteNotificationItem with given NotificationItem") {
                runTest {
                    value = sut.getNotificationItem(uuidOne)
                    value?.let {
                        sut.deleteNotificationItem(it)
                    }
                }
            }

            Then("given NotificationItem should be deleted") {
                runTest {
                    Assertions.assertNull(sut.getNotificationItem(uuidOne))
                }
            }
        }
    }
})
