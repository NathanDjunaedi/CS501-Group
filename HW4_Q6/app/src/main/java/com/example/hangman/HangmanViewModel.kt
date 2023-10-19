package com.example.hangman

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HangmanViewModel() : ViewModel() {
    var currentQuestion: Question? = null
    var questionBank: QuestionBank? = null
    val liveData: MutableLiveData<Question> by lazy {
        MutableLiveData<Question>()
    }

    fun updateData() {
        liveData.value = currentQuestion
    }
}