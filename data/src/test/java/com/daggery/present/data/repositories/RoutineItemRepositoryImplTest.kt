package com.daggery.present.data.repositories

import com.daggery.present.data.db.INotificationItemDao
import com.daggery.present.data.db.IRoutineItemDao
import com.daggery.present.data.db.test.FakeNotificationItemDao
import com.daggery.present.data.mappers.NotificationItemEntityMapper
import com.daggery.present.data.mappers.RoutineItemEntityMapper
import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.repositories.NotificationItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

@ExperimentalCoroutinesApi
internal object RoutineItemRepositoryImplTest : Spek({
    Feature("RoutineItem Repository") {

        lateinit var testCoroutineScheduler: TestCoroutineScheduler
        lateinit var coroutineDispatcher: TestDispatcher

        lateinit var dao: IRoutineItemDao
        lateinit var mapper: RoutineItemEntityMapper
        lateinit var localDataSource: RoutineItemLocalDataSource
        lateinit var sut: NotificationItemRepository

        beforeEachScenario {
            testCoroutineScheduler = TestCoroutineScheduler()
            coroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)

            dao = FakeNotificationItemDao()
            mapper = NotificationItemEntityMapper()
            localDataSource = NotificationItemLocalDataSource(dao, coroutineDispatcher, mapper)
            sut = NotificationItemRepositoryImpl(localDataSource)

            Dispatchers.setMain(coroutineDispatcher)
        }

        afterEachScenario {
            Dispatchers.resetMain()
        }

        Scenario("getting Notifications List Flow") {
            var value: Flow<List<NotificationItem>>? = null

            When("calling getNotificationsFlow()") {
                value = sut.getNotificationsFlow()
            }

            Then("it should return an instance of Flow") {
                Assertions.assertInstanceOf(Flow::class.java, value)
            }
        }

        Scenario("getting Notifications List") {
            var value: List<NotificationItem>? = null

            When("calling getNotifications()") {
                runTest {
                    value = sut.getNotifications()
                }
            }

            Then("it should return an instance of List") {
                Assertions.assertInstanceOf(List::class.java, value)
            }
        }

        Scenario("adding Notification") {
            val valueThree = NotificationItem("3", "Message", 0, 10, false)

            When("calling addNotification() with given argument") {
                runTest {
                    sut.addNotification(valueThree)
                }
            }

            Then("it should add given argument to database") {
                runTest {
                    val value = sut.getNotificationByUuid("3")
                    assertEquals(valueThree, value)
                }
            }
        }

        Scenario("updating Notification") {
            val updatedTwo = NotificationItem("2", "Updated Message", 0, 10, false)

            When("calling updateNotification() with given argument") {
                runTest {
                    sut.updateNotification(updatedTwo)
                }
            }

            Then("it should update corresponding notification uuid with given argument") {
                runTest {
                    val value = sut.getNotificationByUuid("2")
                    assertEquals(updatedTwo, value)
                }
            }
        }

        Scenario("deleting Notification") {

            When("calling deleteNotification() with given argument") {
                runTest {
                    val value = sut.getNotificationByUuid("2")
                    value?.let {
                        sut.deleteNotification(it)
                    }
                }
            }

            Then("it should update corresponding notification uuid with given argument") {
                runTest {
                    val valueTest = sut.getNotificationByUuid("2")
                    assertNull(valueTest)
                }
            }
        }
    }
})