package com.igorganapolsky.vibratingwatchapp

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MainActivityNavigationTests {
    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
            .moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun `Should launch MainActivity with necessary elements`() {
        Espresso.onView(ViewMatchers.withId(R.id.llAddTimerButtonLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.ivTimerListImage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.timerListFragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `Should forward user to next screen`() {
        Espresso.onView(ViewMatchers.withId(R.id.llAddTimerButtonLayout))
            .perform(ViewActions.click())

        MainActivity::class shouldNavigateTo SetTimerActivity::class
    }

    // TODO
    // Test clicking on timerlist item navigates to next screen

    // TODO
    // Test populating timerlist hides image logo

}
