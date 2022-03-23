package com.daggery.present.notificationpage.viewmodel

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class NotificationEditorSheetViewModelTest : Spek({

    describe("NotificationEditorSheet ViewModel Test") {
        val sut = NotificationEditorSheetViewModel()


        describe("#getNotificationByUuid") {
            context("calls this method with given argument") {
                it("returns corresponding notification") {

                }
            }
        }

        describe("#addNotification") {
            context("calls this method with given argument") {
                it("add given argument to database") {

                }
            }
        }

        describe("#updateNotification") {
            context("calls this method with given argument") {
                it("update corresponding notification with new data") {

                }
            }
        }

    }
})
