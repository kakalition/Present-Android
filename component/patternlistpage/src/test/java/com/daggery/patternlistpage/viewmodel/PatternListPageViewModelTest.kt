package com.daggery.patternlistpage.viewmodel

import com.daggery.patternlistpage.entities.PatternListState
import com.daggery.present.data.repositories.test.FakeBreathPatternRepository
import com.daggery.present.data.usecases.breathpattern.*
import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.entities.Day
import com.daggery.present.domain.entities.RoutineItem
import com.daggery.present.domain.repositories.BreathPatternRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

@ExperimentalCoroutinesApi
class PatternListPageViewModelTest : Spek({

    Feature("PatternListPage ViewModel Test") {

        lateinit var testCoroutineScheduler: TestCoroutineScheduler
        lateinit var coroutineDispatcher: TestDispatcher

        lateinit var repository: BreathPatternRepository
        lateinit var getBreathPatternItemsFlowUseCase: GetBreathPatternItemsFlowUseCase
        lateinit var getBreathPatternItemByUuidUseCase: GetBreathPatternItemByUuidUseCase
        lateinit var addBreathPatternUseCase: AddBreathPatternUseCase
        lateinit var updateBreathPatternUseCase: UpdateBreathPatternUseCase
        lateinit var deleteBreathPatternUseCase: DeleteBreathPatternUseCase

        lateinit var sut: PatternListPageViewModel

        beforeEachScenario {
            testCoroutineScheduler = TestCoroutineScheduler()
            coroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)

            repository = FakeBreathPatternRepository()
            getBreathPatternItemsFlowUseCase = GetBreathPatternItemsFlowUseCase(repository)
            getBreathPatternItemByUuidUseCase = GetBreathPatternItemByUuidUseCase(repository)
            addBreathPatternUseCase = AddBreathPatternUseCase(repository)
            updateBreathPatternUseCase = UpdateBreathPatternUseCase(repository)
            deleteBreathPatternUseCase = DeleteBreathPatternUseCase(repository)
            sut = PatternListPageViewModel(
                getBreathPatternItemsFlowUseCase,
                getBreathPatternItemByUuidUseCase,
                addBreathPatternUseCase,
                updateBreathPatternUseCase,
                deleteBreathPatternUseCase
            )

            Dispatchers.setMain(coroutineDispatcher)
        }

        afterEachScenario {
            Dispatchers.resetMain()
        }

        Scenario("collecting PatternListState") {

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(PatternListState.Loading, sut.patternListState.value)
                    sut.changeScreenState(false)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            Then("collected value is emitted to patternListState") {
                runTest {
                    Assertions.assertInstanceOf(PatternListState.Result::class.java, sut.patternListState.value)
                }
            }
        }

        Scenario("getting BreathPatternItem") {
            var value: BreathPatternItem? = null

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(PatternListState.Loading, sut.patternListState.value)
                    sut.changeScreenState(false)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            And("calling getPattern() with given id") {
                runTest {
                    val uuidOne = "1"
                    value = sut.getPatternItem(uuidOne)
                }
            }

            Then("it should return BreathPatternItem with corresponding uuid") {
                runTest {
                    val expected = BreathPatternItem("1", "Pattern 1", 1, 1, 1, 1, 1, 1)
                    Assertions.assertEquals(expected, value)
                }
            }
        }

        Scenario("adding BreathPatternItem") {
            val valueThree = BreathPatternItem("3", "Pattern 3", 3, 3, 3, 3, 3, 3)

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(PatternListState.Loading, sut.patternListState.value)
                    sut.changeScreenState(false)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            And("calling addPattern() with given BreathPatternItem") {
                runTest {
                    sut.addPattern(valueThree)
                }
            }

            Then("given BreathPatternItem should be in database") {
                runTest {
                    val value = sut.getPatternItem("3")
                    Assertions.assertEquals(valueThree, value)
                }
            }
        }

        Scenario("updating BreathPatternItem") {
            val updatedValue = BreathPatternItem("1", "Updated Pattern 1", 1, 1, 1, 1, 1, 1)

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(PatternListState.Loading, sut.patternListState.value)
                    sut.changeScreenState(false)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            And("calling updatePattern() with given RoutineItem") {
                runTest {
                    sut.updatePattern(updatedValue)
                }
            }

            Then("RoutineItem with corresponding uuid should be in updated") {
                runTest {
                    val value = sut.getPatternItem("1")
                    Assertions.assertEquals(updatedValue, value)
                }
            }
        }

        Scenario("deleting BreathPatternItem") {
            val uuidOne = "1"
            var value: BreathPatternItem? = null

            When("calling collectState()") {
                runTest {
                    Assertions.assertEquals(PatternListState.Loading, sut.patternListState.value)
                    sut.changeScreenState(false)
                    val job = launch { sut.collectState() }
                    job.join()
                }
            }

            And("calling deletePattern with given BreathPatternItem") {
                runTest {
                    value = sut.getPatternItem(uuidOne)
                    value?.let {
                        sut.deletePattern(it)
                    }
                }
            }

            Then("given RoutineItem should be deleted") {
                runTest {
                    Assertions.assertNull(sut.getPatternItem(uuidOne))
                }
            }
        }
    }
})
