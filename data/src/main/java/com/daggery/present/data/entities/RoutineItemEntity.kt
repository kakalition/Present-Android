package com.daggery.present.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.daggery.present.domain.entities.Day

@Entity(tableName = "routine_item")
data class RoutineItemEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo (name = "name") val name: String,
    @ColumnInfo (name = "hour") val hour: Int,
    @ColumnInfo (name = "minute") val minute: Int,
    @ColumnInfo (name = "repeat_every") val repeatEvery: List<Day>
)