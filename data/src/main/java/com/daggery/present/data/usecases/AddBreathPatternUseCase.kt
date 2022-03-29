package com.daggery.present.data.usecases

import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.repositories.BreathPatternRepository
import javax.inject.Inject

class AddBreathPatternUseCase @Inject constructor(private val breathPatternRepository: BreathPatternRepository) {
    suspend operator fun invoke(value: BreathPatternItem) {
        breathPatternRepository.addBreathPattern(value)
    }
}