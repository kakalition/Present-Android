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

    private var _breathPatternStateHolder: BreathPatternStateHolder? = null
    val breathPatternStateHolder get() = _breathPatternStateHolder

    suspend fun getBreathPatternStateHolder(uuid: String) {
        _breathPatternStateHolder = getBreathPatternItemByUuidUseCase(uuid)?.let { mapper.toBreathPatternStateHolder(it) }
    }

    suspend fun startsSession() {

    }

}