package com.daggery.patternlistpage.viewmodel

import com.daggery.patternlistpage.entities.PatternListState
import com.daggery.present.data.repositories.test.FakeBreathPatternRepository
import com.daggery.present.data.usecases.breathpattern.DeleteBreathPatternUseCase
import com.daggery.present.data.usecases.breathpattern.GetBreathPatternItemByUuidUseCase
import com.daggery.present.data.usecases.breathpattern.GetBreathPatternItemsFlowUseCase
import com.daggery.present.data.usecases.breathpattern.UpdateBreathPatternUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@ExperimentalCoroutinesApi
class PatternListPageViewModelTest : Spek({

    describe("PatternListPage ViewModel Test") {
        val fakeRepository = FakeBreathPatternRepository()
        val getBreathPatternItemsFlowUseCase = GetBreathPatternItemsFlowUseCase(fakeRepository)
        val getBreathPatternItemByUuidUseCase = GetBreathPatternItemByUuidUseCase(fakeRepository)
        val updateBreathPatternUseCase = UpdateBreathPatternUseCase(fakeRepository)
        val deleteBreathPatternUseCase = DeleteBreathPatternUseCase(fakeRepository)
        val sut = PatternListPageViewModel(
            getBreathPatternItemsFlowUseCase,
            getBreathPatternItemByUuidUseCase,
            updateBreathPatternUseCase,
            deleteBreathPatternUseCase
        )

        lateinit var testCoroutineScheduler: TestCoroutineScheduler
        lateinit var coroutineDispatcher: TestDispatcher

        beforeEachTest {
            testCoroutineScheduler = TestCoroutineScheduler()
            coroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)
            Dispatchers.setMain(coroutineDispatcher)
        }

        afterEachTest {
            Dispatchers.resetMain()
        }

        describe("#collectState") {
            context("calls this method") {
                it("emit value to patternListState") {
                    TestScope(coroutineDispatcher).runTest {
                        var value: PatternListState? = null
                        sut.collectState()
                        sut.changeScreenState(false)

                        val job = launch {
                            sut.patternListState.collect {
                                ensureActive()
                                value = it
                                if (it is PatternListState.Result) cancel()
                            }
                        }

                        job.join()
                        Assertions.assertInstanceOf(PatternListState.Result::class.java, value)
                    }
                }
            }
        }

        describe("#updatePattern") {
            context("calls this method with given argument") {
                it("update corresponding pattern with new data") {
                    runTest {
                        val valueBeforeUpdate = sut.getPattern("1")
                        val updatedValue = valueBeforeUpdate?.copy(name = "Updated Value")
                        Assertions.assertNotNull(updatedValue)

                        updatedValue?.let {
                            sut.updatePattern(it)
                            sut.updatePattern(updatedValue)

                            val value = sut.getPattern("1")
                            Assertions.assertEquals(updatedValue, value)
                        }
                    }
                }
            }
        }

        describe("#deletePattern") {
            context("calls this method with given argument") {
                it("delete corresponding routine with new data") {
                    runTest {
                        val value = sut.getPattern("1")
                        Assertions.assertNotNull(value)

                        value?.let {
                            sut.deletePattern(it)
                            val afterDeleted = sut.getPattern("1")
                            Assertions.assertNull(afterDeleted)
                        }
                    }
                }
            }
        }
    }
})
