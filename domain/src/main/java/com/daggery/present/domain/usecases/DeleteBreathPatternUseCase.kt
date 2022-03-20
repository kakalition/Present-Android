package com.daggery.present.domain.usecases

import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.repositories.BreathPatternRepository

class DeleteBreathPatternUseCase(private val breathPatternRepository: BreathPatternRepository) {
    suspend operator fun invoke(value: BreathPatternItem) {
        breathPatternRepository.deleteBreathPattern(value)
    }
}