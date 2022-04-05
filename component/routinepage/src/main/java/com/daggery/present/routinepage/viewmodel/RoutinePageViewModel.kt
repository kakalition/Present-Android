package com.daggery.present.routinepage.viewmodel

import androidx.lifecycle.ViewModel
import com.daggery.present.data.usecases.routineitem.*
import javax.inject.Inject

class RoutinePageViewModel @Inject constructor(
    private val getRoutinesFlowUseCase: GetRoutineFlowUseCase,
    private val getRoutineByUuidUseCase: GetRoutineByUuidUseCase,
    private val addRoutineUseCase: AddRoutineUseCase,
    private val updateRoutineUseCase: UpdateRoutineUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase
) : ViewModel() {

}
