package com.daggery.present.domain.usecases

import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.repositories.BreathPatternRepository

class AddBreathPatternUseCase(private val breathPatternRepository: BreathPatternRepository) {
    suspend operator fun invoke(value: BreathPatternItem) {
        breathPatternRepository.addBreathPattern(value)
    }
}