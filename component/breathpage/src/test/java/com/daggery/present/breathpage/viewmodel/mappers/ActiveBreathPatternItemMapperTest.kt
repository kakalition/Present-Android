package com.daggery.present.breathpage.viewmodel.mappers

import com.daggery.present.breathpage.viewmodel.entities.ActiveBreathPatternItem
import com.daggery.present.domain.entities.BreathPatternItem
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal class ActiveBreathPatternItemMapperTest : Spek({
    describe("ActiveBreathPatternItem Mapper") {
        val sut = ActiveBreathPatternItemMapper()
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
        val activeBreathPatternItem = ActiveBreathPatternItem(
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
            it("returns ActiveBreathPatternItem from given BreathPatternItem") {
                val value = sut.toActiveBreathPatternItem(breathPatternItem)
                Assertions.assertInstanceOf(ActiveBreathPatternItem::class.java, value)
                Assertions.assertEquals(activeBreathPatternItem, value)
            }
        }

    }
})