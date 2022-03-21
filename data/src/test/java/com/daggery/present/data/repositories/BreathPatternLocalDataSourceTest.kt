package com.daggery.present.data.repositories

import com.daggery.present.data.db.FakeBreathPatternDao
import com.daggery.present.data.mappers.BreathPatternItemEntityMapper
import com.daggery.present.domain.entities.BreathPatternItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@ExperimentalCoroutinesApi
object BreathPatternLocalDataSourceTest : Spek({
    describe("BreathPattern Local Data Source") {
        val dao by memoized { FakeBreathPatternDao() }
        val testCoroutineScheduler = TestCoroutineScheduler()
        val coroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)
        val mapper = BreathPatternItemEntityMapper()
        val sut by memoized { BreathPatternLocalDataSource(dao, coroutineDispatcher, mapper) }

        val uuidOne = "1"
        val valueOne = BreathPatternItem("1", "Pattern 1", 1, 1, 1, 1, 1, 1)
        val updatedValueOne = BreathPatternItem("1", "Pattern 1 Updated", 1, 1, 1, 1, 1, 1)
        val uuidFour = "4"
        val valueFour = BreathPatternItem("4", "Pattern 4", 4, 4, 4, 4, 4, 4)

        describe("Call getBreathPatternItemsFlow()") {
            it("returns Flow<List<BreathPatternItems>>") {
                runTest(testCoroutineScheduler) {
                    val value = sut.getBreathPatternItemsFlow()
                    Assertions.assertInstanceOf(Flow::class.java, value)
                }
            }
        }

        describe("Call getBreathPatternItems()") {
            it("returns List<BreathPatternItems>") {
                runTest(testCoroutineScheduler) {
                    val value = sut.getBreathPatternItems()
                    Assertions.assertInstanceOf(List::class.java, value)
                    val localValueOne = value[0]
                    Assertions.assertInstanceOf(BreathPatternItem::class.java, localValueOne)
                }
            }
        }

        describe("Call getBreathPatternItemByUuid()") {
            it("returns BreathPatternItem with corresponding given uuid") {
                runTest(testCoroutineScheduler) {
                    val value = sut.getBreathPatternItemByUuid(uuidOne)
                    Assertions.assertEquals(valueOne, value)
                }
            }

            it("returns null because there is no BreathPatternItem with corresponding uuid") {
                runTest(testCoroutineScheduler) {
                    val value = sut.getBreathPatternItemByUuid(uuidFour)
                    Assertions.assertEquals(null, value)
                }
            }
        }

        describe("Call addBreathPattern()") {
            it("add given argument to database") {
                runTest(testCoroutineScheduler) {
                    sut.addBreathPattern(valueFour)
                    val value = sut.getBreathPatternItemByUuid(uuidFour)
                    Assertions.assertEquals(valueFour, value)
                }
            }
        }

        describe("Call updateBreathPattern()") {
            it("update given argument to database") {
                runTest(testCoroutineScheduler) {
                    sut.updateBreathPattern(updatedValueOne)
                    val value = sut.getBreathPatternItemByUuid(uuidOne)
                    Assertions.assertEquals(updatedValueOne, value)
                }
            }
        }

        describe("Call deleteBreathPattern()") {
            it("delete given argument from database") {
                runTest(testCoroutineScheduler) {
                    sut.deleteBreathPattern(valueOne)
                    val value = sut.getBreathPatternItemByUuid(uuidOne)
                    Assertions.assertEquals(null, value)
                }
            }
        }

    }
})
