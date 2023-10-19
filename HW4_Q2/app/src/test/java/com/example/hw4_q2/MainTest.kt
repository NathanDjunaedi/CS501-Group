package com.example.hw4_q2

import org.junit.Test
import org.junit.Assert.*

class UnitTest {
    @Test
    fun gravityTest(){
        val mainActivity = MainActivity()
        assertEquals(gravity, 9.81)
    }

    fun sensitivityTest(){
        val mainActivity = MainActivity()
        assertEquals(sensitivity, 0)
    }
}