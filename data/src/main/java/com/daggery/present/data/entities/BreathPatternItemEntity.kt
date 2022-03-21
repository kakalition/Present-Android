package com.daggery.present.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breathPattern")
data class BreathPatternItemEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "total_count") val totalCount: Int,
    @ColumnInfo(name = "inhale_duration") val inhaleDuration: Int,
    @ColumnInfo(name = "hold_after_inhale_duration") val holdAfterInhaleDuration: Int,
    @ColumnInfo(name = "exhale_duration") val exhaleDuration: Int,
    @ColumnInfo(name = "hold_after_duration") val holdAfterExhaleDuration: Int,
    @ColumnInfo(name = "repetitions") val repetitions: Int

)