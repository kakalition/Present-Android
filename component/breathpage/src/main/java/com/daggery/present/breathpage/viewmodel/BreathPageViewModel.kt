package com.daggery.present.breathpage.viewmodel

import androidx.lifecycle.ViewModel
import com.daggery.present.domain.usecases.GetBreathPatternItemByUuidUseCase
import javax.inject.Inject

class BreathPageViewModel @Inject constructor(
    private val timerEngine: TimerEngine,
    private val getBreathPatternItemByUuidUseCase: GetBreathPatternItemByUuidUseCase
): ViewModel() {

}