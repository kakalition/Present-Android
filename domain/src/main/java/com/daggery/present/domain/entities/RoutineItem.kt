package com.daggery.present.domain.entities

data class RoutineItem(
    val uuid: String,
    val name: String,
    val hour: Int,
    val minute: Int,
    val repeatEvery: List<Day>,
    val isActive: Boolean
)