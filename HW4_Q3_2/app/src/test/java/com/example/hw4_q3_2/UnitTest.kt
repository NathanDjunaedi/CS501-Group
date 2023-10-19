package com.example.hw4_q3_2

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {
    @Test
    fun startsOff() {
        assertEquals(flashlightState, false)
    }

    @Test
    fun thresholdsCorrect(){
        assertEquals(swipeThresh, 100)
        assertEquals(swipeVelThresh, 100)
    }
}