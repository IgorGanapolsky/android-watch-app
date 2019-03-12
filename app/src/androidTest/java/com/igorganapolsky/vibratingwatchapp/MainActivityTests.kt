package com.igorganapolsky.vibratingwatchapp

import androidx.test.rule.ActivityTestRule
import com.igorganapolsky.vibratingwatchapp.util.ScreenRobot
import com.igorganapolsky.vibratingwatchapp.util.ScreenRobot.Companion.withRobot
import org.junit.Rule
import org.junit.Test

class MainActivityTests {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, true)

    @Test
    fun onLaunchElements() {
        withRobot(MainScreenRobot::class.java)
            .checkLogoIsDisplayed()
            .checkButtonIsDisplayed()
            .checkFragmentIsDisplayed()
    }

    //TODO:
    // Test that logo is displayed when no timers are present
    //TODO:
    // Test logo is hidden when timers are present

    class MainScreenRobot : ScreenRobot<MainScreenRobot>() {

        fun checkLogoIsDisplayed(): MainScreenRobot {
            return checkIsDisplayed(R.id.ivTimerListImage)
        }

        fun checkButtonIsDisplayed(): MainScreenRobot {
            return checkIsDisplayed(R.id.llAddTimerButtonLayout)
                .checkViewHasText(R.id.addTimerButtonImageLabel, "Add timer")
        }

        fun checkFragmentIsDisplayed(): MainScreenRobot {
            return checkIsDisplayed(R.id.timerListFragment)
        }
    }

}
