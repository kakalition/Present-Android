package com.daggery.present.domain.entities

data class BreathPatternItem(
    val uuid: String,
    val name: String,
    val totalCount: Int,
    val inhaleDuration: Int,
    val holdAfterInhaleDuration: Int,
    val exhaleDuration: Int,
    val holdAfterExhaleDuration: Int,
    val repetitions: Int
)
