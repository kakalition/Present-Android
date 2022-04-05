package com.daggery.present.data.db.test

import com.daggery.present.data.db.interfaces.IBreathPatternDao
import com.daggery.present.data.entities.BreathPatternItemEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FakeBreathPatternDao : IBreathPatternDao {

    private val breathPatternFlow = MutableStateFlow(
        List(3) {
            BreathPatternItemEntity("$it", "Pattern $it", it, it, it, it, it, it)
        }
    )

    override fun getBreathPatternItemEntitiesFlow(): StateFlow<List<BreathPatternItemEntity>> {
        return breathPatternFlow.asStateFlow()
    }

    override suspend fun getBreathPatternItemEntities(): List<BreathPatternItemEntity> {
        return breathPatternFlow.value
    }

    override suspend fun getBreathPatternItemEntityByUuid(uuid: String): BreathPatternItemEntity? {
        return breathPatternFlow.value.singleOrNull { it.uuid == uuid }
    }

    override suspend fun addBreathPattern(value: BreathPatternItemEntity) {
        val newValue = breathPatternFlow.value.toMutableList()
        newValue.add(value)
        breathPatternFlow.emit(newValue)
    }

    override suspend fun updateBreathPattern(value: BreathPatternItemEntity) {
        val newValue = breathPatternFlow.value.map {
            if(it.uuid == value.uuid) { value }
            else it
        }
        breathPatternFlow.emit(newValue)
    }

    override suspend fun deleteBreathPattern(value: BreathPatternItemEntity) {
        val newValue = breathPatternFlow.value.toMutableList()
        newValue.removeIf { it.uuid == value.uuid }
        breathPatternFlow.emit(newValue)
    }
}