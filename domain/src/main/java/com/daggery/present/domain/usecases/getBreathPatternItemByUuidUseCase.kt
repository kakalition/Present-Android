package com.daggery.present.domain.usecases

import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.repositories.BreathPatternRepository

class GetBreathPatternItemByUuidUseCase(private val breathPatternRepository: BreathPatternRepository) {
    suspend operator fun invoke(uuid: String): BreathPatternItem? {
        return breathPatternRepository.getBreathPatternItemByUuid(uuid)
    }
}