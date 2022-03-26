package com.daggery.present.data.usecases

import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.repositories.BreathPatternRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBreathPatternItemsFlowUseCase @Inject constructor(private val breathPatternRepository: BreathPatternRepository) {
    suspend operator fun invoke(): Flow<List<BreathPatternItem>>{
        return breathPatternRepository.getBreathPatternItemsFlow()
    }
}