package com.daggery.present.routinepage.viewmodel

import com.daggery.present.data.repositories.test.FakeRoutineItemRepository
import com.daggery.present.data.usecases.routineitem.*
import com.daggery.present.domain.entities.Day
import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.entities.RoutineItem
import com.daggery.present.domain.repositories.RoutineItemRepository
import com.daggery.present.routinepage.entities.RoutineState
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

        Scenario("collecting routinesState") {

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(RoutineState.Loading, sut.routineState.value)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            Then("collected value is emitted to routinesState") {
                runTest {
                    Assertions.assertInstanceOf(RoutineState.Result::class.java, sut.routineState.value)
                }
            }
        }

        Scenario("getting RoutineItem") {
            var value: RoutineItem? = null

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(RoutineState.Loading, sut.routineState.value)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            And("calling getRoutineItem() with given id") {
                runTest {
                    val uuidOne = "1"
                    value = sut.getRoutineItem(uuidOne)
                }
            }

            Then("it should return RoutineItem with corresponding uuid") {
                runTest {
                    val expected = RoutineItem("1", "Routine 1", 1, 1, listOf(Day.SU, Day.MO), false)
                    Assertions.assertEquals(expected, value)
                }
            }
        }

        Scenario("adding RoutineItem") {
            val valueThree = RoutineItem(
                "3", "Routine 3", 1, 1, listOf(Day.MO), false
            )

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(RoutineState.Loading, sut.routineState.value)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            And("calling addRoutineItem with given RoutineItem") {
                runTest {
                    sut.addRoutineItem(valueThree)
                }
            }

            Then("given RoutineItem should be in database") {
                runTest {
                    val value = sut.getRoutineItem("3")
                    Assertions.assertEquals(valueThree, value)
                }
            }
        }

        Scenario("updating RoutineItem") {
            val updatedValue = RoutineItem(
                "2", "Updated Routine 2", 1, 1, listOf(Day.MO), false
            )

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(RoutineState.Loading, sut.routineState.value)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            And("calling updateRoutineItem() with given RoutineItem") {
                runTest {
                    sut.updateRoutineItem(updatedValue)
                }
            }

            Then("RoutineItem with corresponding uuid should be in updated") {
                runTest {
                    val value = sut.getRoutineItem("2")
                    Assertions.assertEquals(updatedValue, value)
                }
            }
        }

        Scenario("deleting RoutineItem") {
            val uuidOne = "1"
            var value: RoutineItem? = null

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(RoutineState.Loading, sut.routineState.value)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            And("calling deleteRoutineItem with given RoutineItem") {
                runTest {
                    value = sut.getRoutineItem(uuidOne)
                    value?.let {
                        sut.deleteRoutineItem(it)
                    }
                }
            }

            Then("given RoutineItem should be deleted") {
                runTest {
                    Assertions.assertNull(sut.getRoutineItem(uuidOne))
                }
            }
        }
    }
})
