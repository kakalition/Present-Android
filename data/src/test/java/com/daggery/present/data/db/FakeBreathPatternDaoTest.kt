package com.daggery.present.data.db

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@ExperimentalCoroutinesApi
internal class FakeBreathPatternDaoTest : Spek({

    describe("Fake BreathPatternDao") {
        val sut by memoized { FakeBreathPatternDao() }

        describe("Call getBreathPatternItemsFlow()") {
            val value = sut.getBreathPatternItemsFlow()
            it("returns Flow<List<BreathPatternItem>>") {
                Assertions.assertInstanceOf(Flow::class.java, value)
            }
        }

        describe("Call getBreathPatternItems()") {
            runTest {
                val value = sut.getBreathPatternItems()
                it("returns List<BreathPatternItem>") {
                    Assertions.assertInstanceOf(List::class.java, value)
                }
            }
        }

    }
})