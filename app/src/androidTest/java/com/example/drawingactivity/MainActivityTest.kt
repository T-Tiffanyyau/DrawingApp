package com.example.drawingactivity

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    /**
     * This test checks if the initial fragment is the SplashScreen
     */
    @Test
    fun testInitialFragmentIsSplashScreen() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity { activity ->
            val currentFragment =
                activity.supportFragmentManager.findFragmentById(R.id.fragment_container)
            assertTrue(currentFragment is SplashScreen)
        }
    }

}