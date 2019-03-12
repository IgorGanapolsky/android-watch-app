package com.igorganapolsky.vibratingwatchapp

import androidx.test.InstrumentationRegistry
import androidx.test.core.app.ActivityScenario
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SmokeTests {

    /**
     * Verify the Context of the app under test.
     */
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getTargetContext()
        Assert.assertEquals("com.igorganapolsky.vibratingwatchapp", appContext.packageName)
    }

    /**
     * This test will automatically fail if the app crashes for some reason.
     */
    @Test
    fun appLaunchesSuccessfully() {
        ActivityScenario.launch(MainActivity::class.java)
    }

}
