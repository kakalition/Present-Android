package com.daggery.present.breathpage.viewmodel

import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.breathpage.entities.BreathStateEnum
import com.daggery.present.breathpage.mappers.BreathPatternStateHolderMapper
import com.daggery.present.data.repositories.test.FakeBreathPatternRepository
import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.data.usecases.GetBreathPatternItemByUuidUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import javax.inject.Inject

@ExperimentalCoroutinesApi
internal class BreathPageViewModelTest @Inject constructor(
) : Spek({

    describe("BreathPage ViewModel Test") {
        val repository = FakeBreathPatternRepository()
        val mapper = BreathPatternStateHolderMapper()
        val getBreathPatternItemByUuid = GetBreathPatternItemByUuidUseCase(repository)
        val sut = BreathPageViewModel(mapper, getBreathPatternItemByUuid)

        val uuidOne = "1"
        val valueOne =
            mapper.toBreathPatternStateHolder(BreathPatternItem("1", "Pattern 1", 1, 1, 1, 1, 1, 1))
/*
        1 ground
        1 ready
        1 inhale
        1 post inhale
        1 exhale
        1 post exhale
        1 finished
*/
        val totalValueOneEmit = 7

        val uuidFour = "4"

        describe("#getBreathPatternItemStateHolder") {
            context("calls this method with valid uuid") {
                it("returns BreathPatternStateHolder with corresponding uuid") {
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
                        val value: BreathPatternStateHolder? = try {
                            sut.breathPatternStateHolder
                        } catch(e: Exception) {
                            null
                        }
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
                                if (it.currentState == BreathStateEnum.FINISHED) this.cancel()
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
                                if (it.currentState == BreathStateEnum.FINISHED) this.cancel()
                            }
                        }

                        sut.startSession()

                        job.join()

                        Assertions.assertEquals(
                            TimerState(0, BreathStateEnum.FINISHED),
                            value.last()
                        )
                    }
                }
            }
        }

        describe("#pauseSession") {
            context("calls this method") {
                it("pause session timer") {
                    runTest {
                        sut.getBreathPatternStateHolder(uuidOne)
                        val value = mutableListOf<TimerState>()

                        val job = launch {
                            sut.timerState.collect {
                                ensureActive()
                                value.add(it)
                                if (it.currentState == BreathStateEnum.INHALE && it.currentDuration == 1) {
                                    sut.stopSession()
                                    this.cancel()
                                }
                            }
                        }

                        sut.startSession()

                        job.join()

                        Assertions.assertEquals(
                            TimerState(1, BreathStateEnum.INHALE),
                            value.last()
                        )
                    }
                }
            }
        }

        describe("#resetSession") {
            context("calls this method") {
                it("reset timer to ground state") {
                    runTest {
                        sut.getBreathPatternStateHolder(uuidOne)
                        val value = mutableListOf<TimerState>()

                        val job1 = launch {
                            sut.timerState.collect {
                                ensureActive()
                                value.add(it)
                                if (it.currentState == BreathStateEnum.INHALE && it.currentDuration == 1) {
                                    sut.resetSession()
                                    this.cancel()
                                }
                            }
                        }

                        sut.startSession()

                        job1.join()

                        value.add(sut.timerState.value)
                        Assertions.assertEquals(
                            TimerState(0, BreathStateEnum.GROUND),
                            value.last()
                        )
                    }
                }
            }
        }
    }
})