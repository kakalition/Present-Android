package com.daggery.present.breathpage.viewmodel

import com.daggery.present.data.repositories.test.FakeBreathPatternRepository
import com.daggery.present.domain.entities.BreathPatternItem
import com.daggery.present.domain.usecases.GetBreathPatternItemByUuidUseCase
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import javax.inject.Inject

internal class BreathPageViewModelTest @Inject constructor(
): Spek({

    describe("BreathPage ViewModel Test") {
        val repository = FakeBreathPatternRepository()
        val getBreathPatternItemByUuid = GetBreathPatternItemByUuidUseCase(repository)
        val sut = BreathPageViewModel(getBreathPatternItemByUuid)

        val idOne = "1"
        val valueOne = BreathPatternItem("1", "Pattern 1", 1, 1, 1, 1, 1, 1)

        describe("call getBreathPatternByUuid UseCase") {
            it("returns BreathPatternItem with corresponding uuid") {
            }
        }
    }

})