package com.daggery.patternlistpage.viewmodel

import androidx.lifecycle.ViewModel
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import javax.inject.Inject

class PatternListPageViewModelTest : Spek({

    describe("PatternListPage ViewModel Test") {
        val sut = PatternListPageViewModel()

        describe("#getAllPatterns") {
            context("calls this method") {
                it("returns all patterns") { // TODO: Create this dataclass

                }
            }
        }

        describe("#pinPattern") {
            context("calls this method with given argument") {
                it("update given argument to pinned state") {

                }
            }
        }

        describe("#addPattern") {
            context("calls this method with given argument") {
                it("add given argument to database") {

                }
            }
        }

        describe("#deletePattern") {
            context("calls this method with given argument") {
                it("delete corresponding routine with new data") {

                }
            }
        }

    }

})
