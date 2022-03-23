package com.daggery.present.breathpage.mappers

import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.domain.entities.BreathPatternItem
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal class ActiveBreathPatternItemMapperTest : Spek({
    describe("BreathPatternStateHolder Mapper") {
        val sut = BreathPatternStateHolderMapper()
        val breathPatternItem = BreathPatternItem(
            "1",
            "1",
            1,
            1,
            1,
            1,
            1,
            1
        )
        val activeBreathPatternItem = BreathPatternStateHolder(
            "1",
            "1",
            1,
            1,
            1,
            1,
            1,
            1
        )

        describe("Call toActiveBreathPatternItem()") {
            it("returns BreathPatternStateHolder from given BreathPatternItem") {
                val value = sut.toBreathPatternStateHolder(breathPatternItem)
                Assertions.assertInstanceOf(BreathPatternStateHolder::class.java, value)
                Assertions.assertEquals(activeBreathPatternItem, value)
            }
        }

    }
})