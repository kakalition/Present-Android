package com.daggery.present.data.repositories

import com.daggery.present.data.db.interfaces.IBreathPatternDao
import com.daggery.present.data.db.interfaces.INotificationItemDao
import com.daggery.present.data.db.test.FakeBreathPatternDao
import com.daggery.present.data.db.test.FakeNotificationItemDao
import com.daggery.present.data.entities.BreathPatternItemEntity
import com.daggery.present.data.mappers.BreathPatternItemEntityMapper
import com.daggery.present.data.mappers.NotificationItemEntityMapper
import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.repositories.BreathPatternRepository
import com.daggery.present.domain.repositories.NotificationItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.specification.describe

@ExperimentalCoroutinesApi
object BreathPatternRepositoryTest : Spek({

    Feature("BreathPattern Repository Test") {
        lateinit var testCoroutineScheduler: TestCoroutineScheduler
        lateinit var coroutineDispatcher: TestDispatcher

        lateinit var dao: IBreathPatternDao
        lateinit var mapper: BreathPatternItemEntityMapper
        lateinit var localDataSource: BreathPatternLocalDataSource
        lateinit var sut: BreathPatternRepository

        beforeEachScenario {
            testCoroutineScheduler = TestCoroutineScheduler()
            coroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)

            dao = FakeBreathPatternDao()
            mapper = BreathPatternItemEntityMapper()
            localDataSource = BreathPatternLocalDataSource(dao, coroutineDispatcher, mapper)
            sut = BreathPatternRepositoryImpl(localDataSource)

            Dispatchers.setMain(coroutineDispatcher)
        }

        afterEachScenario {
            Dispatchers.resetMain()
        }

        Scenario("getting BreathPattern List Flow") {
            var value: Flow<List<BreathPatternItem>>? = null

            When("calling getBreathPatternItemsFlow()") {
                value = sut.getBreathPatternItemsFlow()
            }

            Then("it should return an instance of Flow") {
                Assertions.assertInstanceOf(Flow::class.java, value)
            }
        }

        Scenario("getting BreathPattern List") {
            var value: List<BreathPatternItem>? = null

            When("calling getBreathPatternItems()") {
                runTest {
                    value = sut.getBreathPatternItems()
                }
            }

            Then("it should return an instance of List") {
                Assertions.assertInstanceOf(List::class.java, value)
            }

            And("contains BreathPatternItem") {
                val testValue = value?.first()
                Assertions.assertInstanceOf(BreathPatternItem::class.java, testValue)
            }
        }

        Scenario("adding BreathPattern") {
            val valueThree = BreathPatternItem("3", "Pattern 3", 3, 3, 3, 3, 3, 3)

            When("calling addBreathPattern() with given argument") {
                runTest {
                    sut.addBreathPattern(valueThree)
                }
            }

            Then("it should add given argument to database") {
                runTest {
                    val value = sut.getBreathPatternItemByUuid("3")
                    Assertions.assertEquals(valueThree, value)
                }
            }
        }

        Scenario("updating BreathPattern") {
            val updatedValue = BreathPatternItem("1", "Updated Pattern 1", 1, 1, 1, 1, 1, 1)

            When("calling updateBreathPattern() with given argument") {
                runTest {
                    sut.updateBreathPattern(updatedValue)
                }
            }

            Then("it should update corresponding BreathPatternItem uuid with given argument") {
                runTest {
                    val value = sut.getBreathPatternItemByUuid("1")
                    Assertions.assertEquals(updatedValue, value)
                }
            }
        }

        Scenario("deleting BreathPatternItem") {

            When("calling deleteNotification() with given argument") {
                runTest {
                    val value = sut.getBreathPatternItemByUuid("2")
                    value?.let {
                        sut.deleteBreathPattern(it)
                    }
                }
            }

            Then("it should update corresponding BreathPatternItem uuid with given argument") {
                runTest {
                    val valueTest = sut.getBreathPatternItemByUuid("2")
                    Assertions.assertNull(valueTest)
                }
            }
        }

    }
})
