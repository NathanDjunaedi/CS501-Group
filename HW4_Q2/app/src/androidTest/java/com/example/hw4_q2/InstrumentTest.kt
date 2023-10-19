package com.example.hw4_q2

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentTest {
    private lateinit var scenario: ActivityScenario<MainActivity>


    @Before
    fun setup(){
        scenario = launch(MainActivity::class.java)
    }
    @After
    fun teardown(){
        scenario.close()
    }

    @Test
    fun textOnLaunch(){
        onView(withId(R.id.textView)).check(ViewAssertions.matches(withText("Sensitivity")))
    }

    fun sensitivityChanged(){
        onView(withId(R.id.sensitivityBar)).perform(seekTo(3))
        onView(withId(R.id.sensitivityBar)).check(hasProgress(3))
    }
}