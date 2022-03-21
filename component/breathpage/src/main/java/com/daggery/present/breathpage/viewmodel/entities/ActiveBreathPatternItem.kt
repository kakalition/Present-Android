package com.daggery.present.breathpage.viewmodel.entities

data class ActiveBreathPatternItem(
    private val uuid: String,
    private val name: String,
    private val totalCount: Int,
    private val inhaleDuration: Int,
    private val holdPostInhaleDuration: Int,
    private val exhaleDuration: Int,
    private val holdPostExhaleDuration: Int,
    private val repetitions: Int,
    private var state: BreathStateEnum = BreathStateEnum.INHALE
) {

    private var totalDuration = (inhaleDuration + holdPostInhaleDuration + exhaleDuration + holdPostExhaleDuration) * repetitions

    fun cycleState() {
        state = when(state) {
            BreathStateEnum.INHALE -> BreathStateEnum.HOLD_POST_INHALE
            BreathStateEnum.HOLD_POST_INHALE -> BreathStateEnum.EXHALE
            BreathStateEnum.EXHALE -> BreathStateEnum.HOLD_POST_EXHALE
            BreathStateEnum.HOLD_POST_EXHALE -> BreathStateEnum.INHALE
        }
    }
}