package com.daggery.present.breathpage.viewmodel.mappers

import com.daggery.present.breathpage.viewmodel.entities.ActiveBreathPatternItem
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