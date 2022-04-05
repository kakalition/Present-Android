package com.daggery.present.routinepage.viewmodel

import com.daggery.present.data.repositories.test.FakeRoutineItemRepository
import com.daggery.present.data.usecases.routineitem.*
import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.domain.repositories.RoutineItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

@ExperimentalCoroutinesApi
class RoutinePageViewModelTest : Spek({

    Feature("RoutinePage ViewModel Test") {

        lateinit var testCoroutineScheduler: TestCoroutineScheduler
        lateinit var coroutineDispatcher: TestDispatcher

        lateinit var repository: RoutineItemRepository
        lateinit var getRoutinesFlowUseCase: GetRoutineFlowUseCase
        lateinit var getRoutineByUuidUseCase: GetRoutineByUuidUseCase
        lateinit var addRoutineUseCase: AddRoutineUseCase
        lateinit var updateRoutineUseCase: UpdateRoutineUseCase
        lateinit var deleteRoutineUseCase: DeleteRoutineUseCase

        lateinit var sut: RoutinePageViewModel

        beforeEachScenario {
            testCoroutineScheduler = TestCoroutineScheduler()
            coroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)

            repository = FakeRoutineItemRepository()
            getRoutinesFlowUseCase = GetRoutineFlowUseCase(repository)
            getRoutineByUuidUseCase = GetRoutineByUuidUseCase(repository)
            addRoutineUseCase = AddRoutineUseCase(repository)
            updateRoutineUseCase = UpdateRoutineUseCase(repository)
            deleteRoutineUseCase = DeleteRoutineUseCase(repository)
            sut = RoutinePageViewModel(
                getRoutinesFlowUseCase,
                getRoutineByUuidUseCase,
                addRoutineUseCase,
                updateRoutineUseCase,
                deleteRoutineUseCase
            )

            Dispatchers.setMain(coroutineDispatcher)
        }

        afterEachScenario {
            Dispatchers.resetMain()
        }

    }
})
