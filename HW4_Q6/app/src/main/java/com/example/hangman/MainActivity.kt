package com.example.hangman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var questionId = (0..5).random()
    private val questions = QuestionBank()
    private var firstQuestion: Question = questions[questionId]
    private lateinit var viewModel: HangmanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = HangmanQuestionFragment()
        val fragmentTwo = LettersFragment()
        viewModel = ViewModelProvider(this)[HangmanViewModel::class.java]
        viewModel.currentQuestion = firstQuestion
        viewModel.questionBank = questions
        viewModel.updateData()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view_top, fragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view_bottom, fragmentTwo)
            .commit()
    }
}

