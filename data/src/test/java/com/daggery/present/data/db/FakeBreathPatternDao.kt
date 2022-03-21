package com.daggery.present.data.db

import com.daggery.present.data.entities.BreathPatternItemEntity
import com.daggery.present.domain.entities.BreathPatternItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class FakeBreathPatternDao : IBreathPatternDao {

    private val breathPatternFlow = MutableStateFlow(
        List(3) {
            BreathPatternItemEntity("$it", "Pattern $it", it, it, it, it, it, it)
        }
    )

    override fun getBreathPatternItemsFlow(): StateFlow<List<BreathPatternItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBreathPatternItems(): List<BreathPatternItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getBreathPatternItemByUuid(uuid: String): BreathPatternItem? {
        TODO("Not yet implemented")
    }

    override suspend fun addBreathPattern(value: BreathPatternItem) {
        TODO("Not yet implemented")
    }

    override suspend fun updateBreathPattern(value: BreathPatternItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBreathPattern(value: BreathPatternItem) {
        TODO("Not yet implemented")
    }
}