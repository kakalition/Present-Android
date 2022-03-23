package com.daggery.present.notificationpage.viewmodel

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class NotificationPageViewModelTest : Spek({

    describe("NotificationPage ViewModel Test") {
        val sut = NotificationPageViewModel()


        describe("#getAllNotification") {
            context("calls this method") {
                it("returns all notification") { // TODO: Create this dataclass

                }
            }
        }

        describe("#toggleNotification") {
            context("calls this method with given argument") {
                it("update given argument to active/inactive state") {

                }
            }
        }

        describe("#deleteNotification") {
            context("calls this method with given argument") {
                it("delete corresponding routine with new data") {

                }
            }
        }

    }
})
