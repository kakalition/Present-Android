package com.daggery.present.breathpage.viewmodel

import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.breathpage.entities.BreathStateEnum
import com.daggery.present.breathpage.helper.TimerState
import com.daggery.present.breathpage.helper.TimerStatePairBuilder
import com.daggery.present.breathpage.helper.TimerStatePairListBuilder
import com.daggery.present.breathpage.mappers.BreathPatternStateHolderMapper
import com.daggery.present.data.repositories.test.FakeBreathPatternRepository
import com.daggery.present.data.usecases.breathpattern.GetBreathPatternItemByUuidUseCase
import com.daggery.present.domain.repositories.BreathPatternRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import javax.inject.Inject

@ExperimentalCoroutinesApi
internal class BreathPageViewModelTest @Inject constructor(
) : Spek({

    Feature("BreathPage ViewModel Test") {

        lateinit var testCoroutineScheduler: TestCoroutineScheduler
        lateinit var coroutineDispatcher: TestDispatcher

        lateinit var repository: BreathPatternRepository
        lateinit var timerStateBuilder: TimerStatePairListBuilder
        lateinit var mapper: BreathPatternStateHolderMapper
        lateinit var getBreathPatternItemByUuid: GetBreathPatternItemByUuidUseCase
        lateinit var sut: BreathPageViewModel

        beforeEachScenario {
            testCoroutineScheduler = TestCoroutineScheduler()
            coroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)

            repository = FakeBreathPatternRepository()
            timerStateBuilder = TimerStatePairListBuilder(TimerStatePairBuilder())
            mapper = BreathPatternStateHolderMapper()
            getBreathPatternItemByUuid = GetBreathPatternItemByUuidUseCase(repository)
            sut = BreathPageViewModel(timerStateBuilder, mapper, getBreathPatternItemByUuid)

            Dispatchers.setMain(coroutineDispatcher)
        }

        afterEachScenario {
            Dispatchers.resetMain()
        }

        Scenario("getting BreathPatternStateHolder") {
            val uuidOne = "1"
            val valueOne = BreathPatternStateHolder(uuidOne, "Pattern 1", 1, 1, 1, 1, 1, 1)

            When("calling getBreathPatternStateHolder with valid uuid") {
                runTest {
                    sut.getBreathPatternStateHolder(uuidOne)
                }
            }

            Then("breathPatternStateHolder will not be null") {
                Assertions.assertNotNull(sut.breathPatternStateHolder)
            }

            Then("breathPatternStateHolder value will correspond to given uuid") {
                Assertions.assertEquals(valueOne, sut.breathPatternStateHolder)
            }
        }

        Scenario("starting session") {
            val uuidOne = "1"
            val emitList = mutableListOf<Pair<TimerState, TimerState>>()
            val totalEmit = 7 // Ground, Ready, Inhale, Hold, Exhale, Hold, Finished

            When("calling startSession and listen to emit value") {
                runTest {
                    sut.getBreathPatternStateHolder(uuidOne)

                    sut.startSession()
                    val job = launch {
                        sut.timerState.collect {
                            ensureActive()
                            emitList.add(it)
                            if (it.first.state == BreathStateEnum.FINISHED) cancel()
                        }
                    }

                    job.join()
                }
            }

            Then("it should emit 7 value") {
                Assertions.assertEquals(totalEmit, emitList.size)
            }

            And("last emit value state should be FINISHED") {
                Assertions.assertEquals(BreathStateEnum.FINISHED, emitList.last().first.state)
            }
        }

        Scenario("stopping session") {
            val uuidOne = "1"
            val emitList = mutableListOf<Pair<TimerState, TimerState>>()
            val totalEmit = 5 // Ground, Ready, Inhale, Hold, Exhale

            When("stopping session when emit value state is EXHALE") {
                runTest {
                    sut.getBreathPatternStateHolder(uuidOne)

                    sut.startSession()
                    val job = launch {
                        sut.timerState.collect {
                            ensureActive()
                            emitList.add(it)
                            if (it.first.state == BreathStateEnum.EXHALE) {
                                val job = launch { sut.stopSession() }
                                job.join()
                            }
                            if (sut.isSessionPaused.value) cancel()
                        }
                    }

                    job.join()
                }
            }

            Then("it should emit 5 value") {
                Assertions.assertEquals(totalEmit, emitList.size)
            }

            And("last emit value state should be EXHALE") {
                Assertions.assertEquals(BreathStateEnum.EXHALE, emitList.last().first.state)
            }
        }

        Scenario("reset session") {
            val uuidOne = "1"
            val emitList = mutableListOf<Pair<TimerState, TimerState>>()
            val totalEmit = 6 // Ground, Ready, Inhale, Hold, Exhale, Ground

            When("resetting session when emit value state is EXHALE") {
                runTest {
                    sut.getBreathPatternStateHolder(uuidOne)

                    sut.startSession()
                    val job = launch {
                        sut.timerState.collect {
                            ensureActive()
                            emitList.add(it)
                            if (it.first.state == BreathStateEnum.EXHALE) {
                                val job = launch { sut.resetSession() }
                                job.join()
                                emitList.add(sut.timerState.value)
                            }
                            if (sut.isSessionPaused.value) cancel()
                        }
                    }

                    job.join()
                }
            }

            Then("it should emit 6 value") {
                Assertions.assertEquals(totalEmit, emitList.size)
            }

            And("last emit value state should be GROUND") {
                Assertions.assertEquals(BreathStateEnum.GROUND, emitList.last().first.state)
            }
        }

    }
})