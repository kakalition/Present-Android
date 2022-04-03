package com.daggery.present.data.repositories.test

import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.repositories.BreathPatternRepository
import kotlinx.coroutines.flow.*

class FakeBreathPatternRepository : BreathPatternRepository{

    private val breathPatternFlow = MutableStateFlow(
        List(3) {
            BreathPatternItem("$it", "Pattern $it", it, it, it, it, it, it)
        }
    )

    override fun getBreathPatternItemsFlow(): StateFlow<List<BreathPatternItem>> {
        return breathPatternFlow.asStateFlow()
    }

    override suspend fun getBreathPatternItems(): List<BreathPatternItem> {
        return breathPatternFlow.value
    }

    override suspend fun getBreathPatternItemByUuid(uuid: String): BreathPatternItem? {
        val value = breathPatternFlow.value.singleOrNull { it.uuid == uuid }
        return value
    }

    override suspend fun addBreathPattern(value: BreathPatternItem) {
        val newValue = breathPatternFlow.value.toMutableList()
        newValue.add(value)
        breathPatternFlow.emit(newValue)
    }

    override suspend fun updateBreathPattern(value: BreathPatternItem) {
        val newValue = breathPatternFlow.value.map {
            if(it.uuid == value.uuid) { value }
            else it
        }
        breathPatternFlow.emit(newValue)
    }

    override suspend fun deleteBreathPattern(value: BreathPatternItem) {
        val newValue = breathPatternFlow.value.toMutableList()
        newValue.removeIf { it.uuid == value.uuid }
        breathPatternFlow.emit(newValue)
    }
}