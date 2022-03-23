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
        val sut = BreathPageViewModel(getBreathPatternItemByUuid)

        describe("#getBreathPatternItemStateHolder") {
            context("calls this method") {
                it("returns BreathPatternItemStateHolder with corresponding uuid") {

                }
            }
        }

        describe("#startSession") {
            context("calls this method") {
                it("starts session timer") {

                }
            }

            context("timer goes off") {
                it("change state to done") {

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