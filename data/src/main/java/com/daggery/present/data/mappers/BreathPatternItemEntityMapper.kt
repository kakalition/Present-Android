package com.daggery.present.data.mappers

import com.daggery.present.data.entities.BreathPatternItemEntity
import com.daggery.present.domain.entities.BreathPatternItem
import javax.inject.Inject

internal class BreathPatternItemEntityMapper @Inject constructor() {
    fun toBreathPatternItemEntity(value: BreathPatternItem): BreathPatternItemEntity {
        return BreathPatternItemEntity(
            value.uuid,
            value.name,
            value.totalCount,
            value.inhaleDuration,
            value.holdAfterInhaleDuration,
            value.exhaleDuration,
            value.holdAfterExhaleDuration,
            value.repetitions
        )
    }

    fun toBreathPatternItem(value: BreathPatternItemEntity): BreathPatternItem {
        return BreathPatternItem(
            value.uuid,
            value.name,
            value.totalCount,
            value.inhaleDuration,
            value.holdAfterInhaleDuration,
            value.exhaleDuration,
            value.holdAfterExhaleDuration,
            value.repetitions
        )
    }
}