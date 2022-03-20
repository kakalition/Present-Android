package com.daggery.present.domain.usecases

import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.repositories.BreathPatternRepository

class GetBreathPatternItemsUseCase(private val breathPatternRepository: BreathPatternRepository) {
    suspend operator fun invoke(): List<BreathPatternItem> {
        return breathPatternRepository.getBreathPatternItems()
    }
}