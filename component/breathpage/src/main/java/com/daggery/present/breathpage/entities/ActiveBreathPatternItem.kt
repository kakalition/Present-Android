package com.daggery.present.breathpage.entities

data class ActiveBreathPatternItem(
    val uuid: String,
    val name: String,
    val totalCount: Int,
    val inhaleDuration: Int,
    val holdPostInhaleDuration: Int,
    val exhaleDuration: Int,
    val holdPostExhaleDuration: Int,
    val repetitions: Int,
    var state: BreathStateEnum = BreathStateEnum.INHALE
)
