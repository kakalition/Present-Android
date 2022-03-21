package com.daggery.present.data.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@ExperimentalCoroutinesApi
object BreathPatternLocalDataSourceTest : Spek({
    describe("BreathPattern Local Data Source") {
        val sut by memoized { BreathPatternLocalDataSource() }

        describe("Get BreathPatternItemsFlow") {
            it("returns Flow<List<BreathPatternItems>>") {
                runTest {
                    val value = sut.getBreathPatternItemsFlow()
                    Assertions.assertInstanceOf(Flow::class.java, value)
                }
            }
        }
    }
})
