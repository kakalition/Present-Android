package com.daggery.present.breathpage.viewmodel

import com.daggery.present.breathpage.mappers.BreathPatternStateHolderMapper
import com.daggery.present.data.repositories.test.FakeBreathPatternRepository
import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.usecases.GetBreathPatternItemByUuidUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
            mapper.toBreathPatternStateHolder(BreathPatternItem("1", "Pattern 1", 1, 1, 1, 1, 1, 1))

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
                        val value = sut.breathPatternStateHolder
                        Assertions.assertEquals(null, value)
                    }
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