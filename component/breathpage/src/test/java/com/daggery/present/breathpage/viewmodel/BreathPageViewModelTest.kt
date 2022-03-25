package com.daggery.present.breathpage.viewmodel

import com.daggery.present.breathpage.entities.BreathStateEnum
import com.daggery.present.breathpage.mappers.BreathPatternStateHolderMapper
import com.daggery.present.data.repositories.test.FakeBreathPatternRepository
import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.usecases.GetBreathPatternItemByUuidUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import javax.inject.Inject

@ExperimentalCoroutinesApi
internal class BreathPageViewModelTest @Inject constructor(
): Spek({

    describe("BreathPage ViewModel Test") {
        val repository = FakeBreathPatternRepository()
        val mapper = BreathPatternStateHolderMapper()
        val getBreathPatternItemByUuid = GetBreathPatternItemByUuidUseCase(repository)
        val sut = BreathPageViewModel(mapper, getBreathPatternItemByUuid)

        val uuidOne = "1"
        val valueOne =
            mapper.toBreathPatternStateHolder(BreathPatternItem("1", "Pattern 1", 1, 2, 2, 2, 2, 1))
/*
        1 ground
        3 ready
        1 inhale
        1 post inhale
        1 exhale
        1 post exhale
        1 finished
*/
        val totalValueOneEmit = 9

        val uuidFour = "4"

        describe("#getBreathPatternItemStateHolder") {
            context("calls this method with valid uuid") {
                it("returns BreathPatternStateHolder with corresponding uuid") {
                    println("")
                    runTest {
                        sut.getBreathPatternStateHolder(uuidOne)
                        val value = sut.breathPatternStateHolder
                        Assertions.assertEquals(valueOne, value)
                    }
                }
            }

            context("calls this method with invalid uuid") {
                it("returns null") {
                    runTest {
                        sut.getBreathPatternStateHolder(uuidFour)
                        val value = sut.breathPatternStateHolder
                        Assertions.assertEquals(null, value)
                    }
                }
            }
        }

        describe("#startSession") {
            context("calls this method") {
                it("starts session timer") {
                    runTest {
                        sut.getBreathPatternStateHolder(uuidOne)
                        val value = mutableListOf<TimerState>()

                        val job = launch {
                            sut.timerState.collect {
                                ensureActive()
                                value.add(it)
                                println(it)
                                if(it.currentState == BreathStateEnum.FINISHED) this.cancel()
                            }
                        }

                        sut.startSession()

                        job.join()
                        Assertions.assertEquals(totalValueOneEmit, value.size)
                    }
                }
            }

            context("timer goes off") {
                it("change state to done") {
                    runTest {
                        sut.getBreathPatternStateHolder(uuidOne)
                        val value = mutableListOf<TimerState>()

                        val job = launch {
                            sut.timerState.collect {
                                ensureActive()
                                value.add(it)

                                // + 1 for last emit
                                if (it.currentDuration >= sut.totalDuration + 2) {
                                    this.cancel()
                                }
                            }
                        }

                        launch {
                            sut.startSession()
                        }

                        job.join()

                        // Original emit is 4, with last emit is 5
                        Assertions.assertEquals(TimerState(0, BreathStateEnum.FINISHED), value.last())
                    }
                }
            }
        }

        describe("#pauseSession") {
            context("calls this method") {
                it("pause session timer") {

                }
            }
        }

        describe("#resetSession") {
            context("calls this method") {
                it("reset timer to ground state") {

                }
            }
        }
    }
})