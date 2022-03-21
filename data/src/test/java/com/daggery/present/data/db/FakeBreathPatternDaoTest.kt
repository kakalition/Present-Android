package com.daggery.present.data.db

import com.daggery.present.data.entities.BreathPatternItemEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.describe

@ExperimentalCoroutinesApi
internal class FakeBreathPatternDaoTest : Spek({

    describe("Fake BreathPatternDao") {
        val sut by memoized(mode = CachingMode.TEST) { FakeBreathPatternDao() }
        val uuidOne = "1"
        val valueOne = BreathPatternItemEntity("1", "Pattern 1", 1, 1, 1, 1, 1, 1)
        val updatedValueOne = BreathPatternItemEntity("1", "Pattern 1 Updated", 1, 1, 1, 1, 1, 1)
        val uuidFour = "4"
        val valueFour = BreathPatternItemEntity("4", "Pattern 4", 4, 4, 4, 4, 4, 4)

        describe("Call getBreathPatternItemsFlow()") {
            it("returns Flow<List<BreathPatternItem>>") {
                val value = sut.getBreathPatternItemEntitiesFlow()
                Assertions.assertInstanceOf(Flow::class.java, value)
            }
        }

        describe("Call getBreathPatternItems()") {
            it("returns List<BreathPatternItem>") {
                runTest {
                    val value = sut.getBreathPatternItemEntities()
                    Assertions.assertInstanceOf(List::class.java, value)
                }
            }
        }

        describe("Call getBreathPatternByUuid()") {
            it("returns BreathPatternItem with corresponding uuid") {
                runTest {
                    val value = sut.getBreathPatternItemEntityByUuid(uuidOne)
                    Assertions.assertEquals(valueOne, value)
                }
            }

            it("returns null because there is no BreathPatternItemEntity with corresponding uuid") {
                runTest {
                    val value = sut.getBreathPatternItemEntityByUuid(uuidFour)
                    Assertions.assertEquals(null, value)
                }
            }
        }

        describe("Call addBreathPattern()") {
            it("add given argument to database") {
                runTest {
                    sut.addBreathPattern(valueFour)
                    val value = sut.getBreathPatternItemEntityByUuid(uuidFour)
                    Assertions.assertEquals(valueFour, value)
                }
            }
        }

        describe("Call updateBreathPattern()") {
            it("update given argument to database") {
                runTest {
                    sut.updateBreathPattern(updatedValueOne)
                    val value = sut.getBreathPatternItemEntityByUuid(uuidOne)
                    Assertions.assertEquals(updatedValueOne, value)
                }
            }
        }

        describe("Call deleteBreathPattern()") {
            it("delete given argument from database") {
                runTest {
                    sut.deleteBreathPattern(valueOne)
                    val value = sut.getBreathPatternItemEntityByUuid(uuidOne)
                    Assertions.assertEquals(null, value)
                }
            }
        }
    }
})