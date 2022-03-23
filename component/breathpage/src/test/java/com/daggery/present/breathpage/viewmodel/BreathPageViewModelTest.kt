package com.daggery.present.breathpage.viewmodel

import com.daggery.present.breathpage.entities.ActiveBreathPatternItem
import com.daggery.present.data.repositories.test.FakeBreathPatternRepository
import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.usecases.GetBreathPatternItemByUuidUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import javax.inject.Inject

@ExperimentalCoroutinesApi
internal class BreathPageViewModelTest @Inject constructor(
): Spek({

    describe("BreathPage ViewModel Test") {
        val repository = FakeBreathPatternRepository()
        val getBreathPatternItemByUuid = GetBreathPatternItemByUuidUseCase(repository)
        val timerEngine = TimerEngine()
        val sut = BreathPageViewModel(timerEngine, getBreathPatternItemByUuid)
        val item = ActiveBreathPatternItem("1", "1", 1, 4, 4, 4, 4, 1)

        val idOne = "1"
        val valueOne = BreathPatternItem("1", "Pattern 1", 1, 1, 1, 1, 1, 1)

        describe("call getBreathPatternByUuid UseCase") {
            it("returns BreathPatternItem with corresponding uuid") {
                runBlocking {
                    timerEngine.setBreathPattern(item)
                    launch {
                        timerEngine.startTimer()
                    }
                    launch {
                        delay(2000)
                        println("pause")
                        timerEngine.pauseTimer()
                    }
                    launch {
                        delay(4000)
                        println("resume")
                        timerEngine.resumeTimer()
                    }
                    launch {
                        delay(6000)
                        println("reset")
                        timerEngine.resetTimer()
                    }
                }
            }
        }
    }

})