package com.daggery.present.data.db.converter

import androidx.room.TypeConverter
import com.daggery.present.domain.entities.Day

class DayConverter {
    @TypeConverter
    fun stringToList(value: String): List<Day> {
        val splitValue = value.split(",")
        val dayList = mutableListOf<Day>()
        splitValue.forEach {
            dayList.add(Day.valueOf(it))
        }
        return dayList
    }

    @TypeConverter
    fun listToString(value: List<Day>): String {
        var string = ""
        value.forEach {
            string += it.name + ","
        }
        return string.dropLast(1)
    }
}