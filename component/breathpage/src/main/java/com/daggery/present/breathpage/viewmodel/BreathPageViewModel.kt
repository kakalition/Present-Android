package com.daggery.present.breathpage.viewmodel

import androidx.lifecycle.ViewModel
import com.daggery.present.breathpage.entities.BreathPatternStateHolder
import com.daggery.present.breathpage.mappers.BreathPatternStateHolderMapper
import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.usecases.GetBreathPatternItemByUuidUseCase
import javax.inject.Inject

class BreathPageViewModel @Inject constructor(
    private val mapper: BreathPatternStateHolderMapper,
    private val getBreathPatternItemByUuidUseCase: GetBreathPatternItemByUuidUseCase
): ViewModel() {

    suspend fun getBreathPatternStateHolder(uuid: String): BreathPatternStateHolder? {
        return getBreathPatternItemByUuidUseCase(uuid)?.let { mapper.toBreathPatternStateHolder(it) }
    }

}