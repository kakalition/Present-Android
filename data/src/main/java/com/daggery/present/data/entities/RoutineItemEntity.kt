package com.daggery.present.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.daggery.present.data.db.converter.DayConverter
import com.daggery.present.domain.entities.Day

@Entity(tableName = "routine_item")
@TypeConverters(DayConverter::class)
data class RoutineItemEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "hour") val hour: Int,
    @ColumnInfo(name = "minute") val minute: Int,
    @ColumnInfo(name = "repeat_every") val repeatEvery: List<Day>,
    @ColumnInfo(name = "is_active") val isActive: Boolean
)