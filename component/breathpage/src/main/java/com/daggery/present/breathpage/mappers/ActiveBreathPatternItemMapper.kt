package com.daggery.present.breathpage.mappers

import com.daggery.present.breathpage.entities.ActiveBreathPatternItem
import com.daggery.present.domain.entities.BreathPatternItem

internal class ActiveBreathPatternItemMapper {
    fun toActiveBreathPatternItem(value: BreathPatternItem): ActiveBreathPatternItem {
        return ActiveBreathPatternItem(
            value.uuid,
            value.name,
            value.totalCount,
            value.inhaleDuration,
            value.holdPostInhaleDuration,
            value.exhaleDuration,
            value.holdPostExhaleDuration,
            value.repetitions
        )
    }
}