package com.example.hw3_q6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random
import android.content.Context
import android.content.Intent

class CardActivity : AppCompatActivity() {
    // Lists
    private lateinit var firstNumberList: MutableList<Int>
    private lateinit var secondNumberList: MutableList<Int>
    private lateinit var operatorList: MutableList<String>

    // Text Fields
    private lateinit var firstNumber: TextView
    private lateinit var secondNumber: TextView
    private lateinit var operator: TextView

    // EditText Field
    private lateinit var userAnswer: EditText

    // Buttons
    private lateinit var generateProblems: android.widget.Button
    private lateinit var submitAnswer: android.widget.Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)

        val numQuestions = 10
        var numCorrect = 0
        var currentQuestion = 0
        var generateLock = false

        // Initialization of EditText field
        userAnswer = findViewById(R.id.editTextNumber)

        // Initialization of Buttons
        generateProblems = findViewById(R.id.generateProblems)
        submitAnswer = findViewById(R.id.submitButton)

        // Initialization of TextFields
        firstNumber = findViewById(R.id.firstNumber)
        secondNumber = findViewById(R.id.secondNumber)
        operator = findViewById(R.id.operator)

        // Listeners
        generateProblems.setOnClickListener{
            // Check to make sure none in progress
            if (!generateLock){
                // Generate arrays
                generateLists(numQuestions)

                // Sets text for first time use
                setQuestion(currentQuestion)
                generateLock = true
            }
        }

        submitAnswer.setOnClickListener{
            // Error checks
            if(generateLock && userAnswer.text.toString() != ""){
                // Checking if user's answer was correct
                if(evaluateAnswer(currentQuestion)){ numCorrect += 1 }
                currentQuestion += 1

                // End of cards condition
                if(currentQuestion > 9){
                    // Reset lists and give number of correct answers
                    Toast.makeText(this, "Number Correct: $numCorrect", Toast.LENGTH_LONG).show()
                    resetQuestions()
                    currentQuestion = 0
                    generateLock = false
                } else{
                    // Set second set of numbers for question
                    setQuestion(currentQuestion)
                }
            }
        }
    }

    private fun resetQuestions(){
        firstNumberList.clear()
        secondNumberList.clear()
        operatorList.clear()
        userAnswer.setText("")
        firstNumber.text = ""
        operator.text = ""
        secondNumber.text = ""
    }

    private fun setQuestion(currentQuestion: Int){
        firstNumber.text = firstNumberList[currentQuestion].toString()
        secondNumber.text = secondNumberList[currentQuestion].toString()
        operator.text = operatorList[currentQuestion]
        userAnswer.setText("")
    }

    private fun evaluateAnswer(currentQuestion: Int): Boolean {
        // Grab user input and current operator
        val userInput = Integer.parseInt(userAnswer.text.toString())
        // Evaluate to true/false (Probably should convert to switch statement)
        val currentAnswer: Int = if(operatorList[currentQuestion] == "+"){
            firstNumberList[currentQuestion] + secondNumberList[currentQuestion]
        } else{
            firstNumberList[currentQuestion] - secondNumberList[currentQuestion]
        }
        return currentAnswer == userInput
    }

    private fun generateLists(numQuestions: Int){
        // Generates a list of random numbers for each list
        firstNumberList = MutableList(numQuestions) { Random.nextInt(1, 100) }
        secondNumberList = MutableList(numQuestions) { Random.nextInt(1, 21) }
        val tempOperatorList = List(numQuestions) {Random.nextInt(0, 2)}

        // Converts random numbers into operators
        operatorList = MutableList(numQuestions) {""}
        for (index in tempOperatorList.indices){
            if (tempOperatorList[index] == 0){ operatorList[index] = "+" } else { operatorList[index] = "-" }
        }
    }

    companion object{
        fun newIntent(packageContext: Context): Intent{
            return Intent(packageContext, CardActivity::class.java).apply{}
        }
    }

}