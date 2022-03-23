package com.daggery.present.routinepage.viewmodel

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class RoutineEditorSheetViewModelTest : Spek({

    describe("RoutineEditor ViewModel Test") {
        val sut = RoutineEditorSheetViewModel()

        describe("#getRoutineByUuid") {
            context("calls this method with given argument") {
                it("returns corresponding routine") {

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

    }
})
