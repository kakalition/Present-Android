package com.daggery.present.data.usecases.breathpattern

import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.repositories.BreathPatternRepository
import javax.inject.Inject

class GetBreathPatternItemsUseCase @Inject constructor(private val breathPatternRepository: BreathPatternRepository) {
    suspend operator fun invoke(): List<BreathPatternItem> {
        return breathPatternRepository.getBreathPatternItems()
    }
}