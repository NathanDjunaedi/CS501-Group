package com.example.hangman

class QuestionBank {
    operator fun get(questionId: Int): Question {
        return bank[questionId]
    }

    private val bank = MutableList(6) { _ -> Question()}
    init {
        bank[0].setPars("banana", "Fruits")
        bank[1].setPars("guava", "Fruits")
        bank[2].setPars("spider", "Insects")
        bank[3].setPars("beetle", "Insects")
        bank[4].setPars("radio", "Appliances")
        bank[5].setPars("oven", "Appliances")
    }
}