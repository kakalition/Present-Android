package com.daggery.present.data.repositories

import com.daggery.present.data.db.interfaces.IRoutineItemDao
import com.daggery.present.data.db.test.FakeRoutineItemDao
import com.daggery.present.data.mappers.RoutineItemEntityMapper
import com.daggery.present.domain.entities.RoutineItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions
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
        lateinit var sut: RoutineItemRepositoryImpl

        beforeEachScenario {
            testCoroutineScheduler = TestCoroutineScheduler()
            coroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)

            dao = FakeRoutineItemDao()
            mapper = RoutineItemEntityMapper()
            localDataSource = RoutineItemLocalDataSource(dao, coroutineDispatcher, mapper)
            sut = RoutineItemRepositoryImpl(localDataSource)

            Dispatchers.setMain(coroutineDispatcher)
        }

        afterEachScenario {
            Dispatchers.resetMain()
        }

        Scenario("getting Routine List Flow") {
            var value: Flow<List<RoutineItem>>? = null

            When("calling getRoutinesFlow()") {
                value = sut.getRoutinesFlow()
            }

            Then("it should return an instance of Flow") {
                Assertions.assertInstanceOf(Flow::class.java, value)
            }
        }

        Scenario("getting Routine List") {
            var value: List<RoutineItem>? = null

            When("calling getRoutines()") {
                runTest {
                    value = sut.getRoutines()
                }
            }

            Then("it should return an instance of List") {
                Assertions.assertInstanceOf(List::class.java, value)
            }
        }

        Scenario("adding Routine") {
            val valueThree = RoutineItem("3", "Message", 0, 10, listOf())

            When("calling addRoutine() with given argument") {
                runTest {
                    sut.addRoutine(valueThree)
                }
            }

            Then("it should add given argument to database") {
                runTest {
                    val value = sut.getRoutineByUuid("3")
                    Assertions.assertEquals(valueThree, value)
                }
            }
        }

        Scenario("updating Notification") {
            val updatedTwo = RoutineItem("2", "Updated Message", 0, 10, listOf())

            When("calling updateNotification() with given argument") {
                runTest {
                    sut.updateRoutine(updatedTwo)
                }
            }

            Then("it should update corresponding notification uuid with given argument") {
                runTest {
                    val value = sut.getRoutineByUuid("2")
                    Assertions.assertEquals(updatedTwo, value)
                }
            }
        }

        Scenario("deleting Notification") {

            When("calling deleteNotification() with given argument") {
                runTest {
                    val value = sut.getRoutineByUuid("2")
                    value?.let {
                        sut.deleteRoutine(it)
                    }
                }
            }

            Then("it should update corresponding notification uuid with given argument") {
                runTest {
                    val valueTest = sut.getRoutineByUuid("2")
                    Assertions.assertNull(valueTest)
                }
            }
        }
    }
})