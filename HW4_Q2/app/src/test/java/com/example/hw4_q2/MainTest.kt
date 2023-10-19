package com.example.hw4_q2

import org.junit.Test
import org.junit.Assert.*

class UnitTest {
    @Test
    fun gravityTest(){
        assertEquals(gravity, 9.81)
    }

    fun sensitivityTest(){
        assertEquals(sensitivity, 0)
    }
}