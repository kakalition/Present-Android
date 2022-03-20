package com.daggery.present.domain.usecases

import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.repositories.BreathPatternRepository
import kotlinx.coroutines.flow.Flow

class GetBreathPatternItemsFlowUseCase(private val breathPatternRepository: BreathPatternRepository) {
    suspend operator fun invoke(): Flow<List<BreathPatternItem>>{
        return breathPatternRepository.getBreathPatternItemsFlow()
    }
}