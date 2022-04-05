package com.daggery.patternlistpage.entities

import com.daggery.present.domain.entities.BreathPatternItem

sealed class PatternListState {
    object Loading : PatternListState()
    data class Result(val value: List<BreathPatternItem>) : PatternListState()
    data class Error(val error: Throwable) : PatternListState()
}