package com.daggery.present.domain.entities

data class BreathPatternItem(
    val uuid: String,
    val name: String,
    val totalCount: Int,
    val inhaleDuration: Int,
    val holdPostInhaleDuration: Int,
    val exhaleDuration: Int,
    val holdPostExhaleDuration: Int,
    val repetitions: Int
)
