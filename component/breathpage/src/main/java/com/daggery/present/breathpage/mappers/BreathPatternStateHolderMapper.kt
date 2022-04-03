package com.daggery.present.breathpage.mappers

import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.domain.entities.BreathPatternItem
import javax.inject.Inject

class BreathPatternStateHolderMapper @Inject constructor() {
    fun toBreathPatternStateHolder(value: BreathPatternItem): BreathPatternStateHolder {
        return BreathPatternStateHolder(
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