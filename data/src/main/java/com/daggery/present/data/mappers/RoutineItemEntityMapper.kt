package com.daggery.present.data.mappers

import com.daggery.present.data.entities.RoutineItemEntity
import com.daggery.present.domain.entities.RoutineItem
import javax.inject.Inject

class RoutineItemEntityMapper @Inject constructor(){
    fun toRoutineItem(value: RoutineItemEntity): RoutineItem {
        return RoutineItem(
            value.uuid,
            value.name,
            value.hour,
            value.minute,
            value.repeatEvery
        )
    }

    fun toRoutineItemEntity(value: RoutineItem): RoutineItemEntity {
        return RoutineItemEntity(
            value.uuid,
            value.name,
            value.hour,
            value.minute,
            value.repeatEvery
        )
    }
}