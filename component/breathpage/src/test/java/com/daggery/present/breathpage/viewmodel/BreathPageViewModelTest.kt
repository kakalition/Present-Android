package com.daggery.present.breathpage.viewmodel

import com.daggery.present.data.repositories.test.FakeBreathPatternRepository
import com.daggery.present.domain.usecases.GetBreathPatternItemByUuid
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import javax.inject.Inject

internal class BreathPageViewModelTest @Inject constructor(
): Spek({

    describe("BreathPage ViewModel Test") {
        val repository = FakeBreathPatternRepository()
        val getBreathPatternItemById = GetBreathPatternItemByUuid(repository)
        val sut = BreathPageViewModel(getBreathPatternItemById)
    }

})