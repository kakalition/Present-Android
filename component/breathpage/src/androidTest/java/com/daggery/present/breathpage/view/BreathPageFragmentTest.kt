package com.daggery.present.breathpage.view

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daggery.present.breathpage.R
import com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckResultBaseUtils
import org.junit.After
import org.junit.Before
import org.junit.Test

//@RunWith(AndroidJUnit4::class)
internal class BreathPageFragmentTest {

    private lateinit var scenario: FragmentScenario<BreathPageFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
    }

    @Test
    fun when_initially_open_show_initial_breath_pattern_state() {
        scenario.onFragment {
        }
    }
}