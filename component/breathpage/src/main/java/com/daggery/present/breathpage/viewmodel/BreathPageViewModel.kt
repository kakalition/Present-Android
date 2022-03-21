package com.daggery.present.breathpage.viewmodel

import androidx.lifecycle.ViewModel
import com.daggery.present.domain.usecases.GetBreathPatternItemByUuid
import javax.inject.Inject

class BreathPageViewModel @Inject constructor(
    private val getBreathPatternItemByUuid: GetBreathPatternItemByUuid
): ViewModel() {

}