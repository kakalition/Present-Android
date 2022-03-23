package com.daggery.present.routinepage.viewmodel

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class RoutinePageViewModelTest : Spek({
    describe("RoutinePage ViewModel Test") {
        val sut = BreathPageViewModel()

        describe("#getAllRoutines") {
            context("calls this method") {
                it("returns all routines") { // TODO: Create this dataclass

                }
            }
        }

        describe("#toggleRoutine") {
            context("calls this method with given argument") {
                it("update given argument to active/inactive state") {

                }
            }
        }

        describe("#addRoutine") {
            context("calls this method with given argument") {
                it("add given argument to database") {

                }
            }
        }

        describe("#updateRoutine") {
            context("calls this method with given argument") {
                it("update corresponding routine with new data") {

                }
            }
        }

        describe("#deleteRoutine") {
            context("calls this method with given argument") {
                it("delete corresponding routine with new data") {

                }
            }
        }

    }
})
