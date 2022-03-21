package com.daggery.present.data.mappers

import com.daggery.present.data.entities.BreathPatternItemEntity
import com.daggery.present.domain.entities.BreathPatternItem
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object BreathPatternItemEntityMapperTest : Spek({

    describe("BreathPatternItemEntity Mapper") {
        val sut by memoized { BreathPatternItemEntityMapper() }
        val fakeBreathPatternItem = BreathPatternItem("-", "Fake", 0, 1, 1, 1, 1, 0)
        val fakeBreathPatternItemEntity = BreathPatternItemEntity("-", "Fake", 0, 1, 1, 1, 1, 0)

        describe("calling toBreathPatternItemEntity()") {
            it("returns BreathPatternItemEntity") {
                val value = sut.toBreathPatternItemEntity(fakeBreathPatternItem)
                Assertions.assertEquals(fakeBreathPatternItemEntity, value)
            }
        }

        describe("calling toBreathPatternItem()") {
            it("returns BreathPatternItem") {
                val value = sut.toBreathPatternItem(fakeBreathPatternItemEntity)
                Assertions.assertEquals(fakeBreathPatternItem, value)
            }
        }

    }

})