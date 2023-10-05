package com.example.hw3_q6

import org.junit.Assert.*
import org.junit.Test

class CardActivityTest{
    @Test
    fun startsAtZero() {
        val cardActivity = CardActivity()
        assertEquals(currentQuestion, 0)
    }
    @Test
    fun startsUnlocked(){
        val cardActivity = CardActivity()
        assertEquals(generateLock, false)
    }
}