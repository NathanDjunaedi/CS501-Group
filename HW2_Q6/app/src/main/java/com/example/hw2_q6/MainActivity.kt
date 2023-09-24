package com.example.hw2_q6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.w3c.dom.Text
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private lateinit var button0: android.widget.Button
    private lateinit var button1: android.widget.Button
    private lateinit var button2: android.widget.Button
    private lateinit var button3: android.widget.Button
    private lateinit var button4: android.widget.Button
    private lateinit var button5: android.widget.Button
    private lateinit var button6: android.widget.Button
    private lateinit var button7: android.widget.Button
    private lateinit var button8: android.widget.Button
    private lateinit var button9: android.widget.Button
    private lateinit var buttonAdd: android.widget.Button
    private lateinit var buttonSubtract: android.widget.Button
    private lateinit var buttonMultiply: android.widget.Button
    private lateinit var buttonDivide: android.widget.Button
    private lateinit var buttonSqrt: android.widget.Button
    private lateinit var buttonClear: android.widget.Button
    private lateinit var buttonDecimal: android.widget.Button
    private lateinit var buttonEquals: android.widget.Button
    private lateinit var textField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button0 = findViewById(R.id.button0)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button9 = findViewById(R.id.button9)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonSubtract = findViewById(R.id.buttonSubtract)
        buttonDivide = findViewById(R.id.buttonDivide)
        buttonMultiply = findViewById(R.id.buttonMultiply)
        buttonSqrt = findViewById(R.id.buttonSqrt)
        buttonClear = findViewById(R.id.buttonClear)
        buttonDecimal = findViewById(R.id.buttonDecimal)
        buttonEquals = findViewById(R.id.buttonEquals)

        textField = findViewById(R.id.calcResultText)

        var currentText: String
        var firstNumber = ""
        var operatorPressed = ""
        
         /*
         Error message occurs when divide by 0 or other illegal action occurs.
         This causes lockUntilAC to be true, which locks the calculator until the user presses AC
          */
        val errorMessage = "ERR: PRESS AC"
        var lockUntilAC = false

        // Listeners for all of the number buttons
        button0.setOnClickListener{
            if(!lockUntilAC){
                currentText = textField.text.toString() + "0"
                textField.setText(currentText)
            }
        }
        button1.setOnClickListener{
            if(!lockUntilAC){
                currentText = textField.text.toString() + "1"
                textField.setText(currentText)
            }
        }
        button2.setOnClickListener{
            if(!lockUntilAC){
                currentText = textField.text.toString() + "2"
                textField.setText(currentText)
            }
        }
        button3.setOnClickListener{
            if(!lockUntilAC){
                currentText = textField.text.toString() + "3"
                textField.setText(currentText)
            }
        }
        button4.setOnClickListener{
            if(!lockUntilAC){
                currentText = textField.text.toString() + "4"
                textField.setText(currentText)
            }
        }
        button5.setOnClickListener{
            if(!lockUntilAC){
                currentText = textField.text.toString() + "5"
                textField.setText(currentText)
            }
        }
        button6.setOnClickListener{
            if(!lockUntilAC){
                currentText = textField.text.toString() + "6"
                textField.setText(currentText)
            }
        }
        button7.setOnClickListener{
            if(!lockUntilAC){
                currentText = textField.text.toString() + "7"
                textField.setText(currentText)
            }
        }
        button8.setOnClickListener{
            if(!lockUntilAC){
                currentText = textField.text.toString() + "8"
                textField.setText(currentText)
            }
        }
        button9.setOnClickListener{
            if(!lockUntilAC){
                currentText = textField.text.toString() + "9"
                textField.setText(currentText)
            }
        }
        buttonDecimal.setOnClickListener{
            if(!lockUntilAC){
                if (!textField.text.toString().contains(".")) {
                    currentText = textField.text.toString() + "."
                    textField.setText(currentText)
                }
            }
        }

        // Listener to clear everything out
        buttonClear.setOnClickListener{
            currentText = ""
            textField.setText(currentText)
            when(operatorPressed){
                "+" -> buttonAdd.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
                "-" -> buttonSubtract.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
                "*" -> buttonMultiply.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
                "/" -> buttonDivide.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
            }
            operatorPressed = ""
            firstNumber = null.toString()
            lockUntilAC = false
        }

        // Listeners for operations
        buttonAdd.setOnClickListener{
            if (operatorPressed == "" && !lockUntilAC){
                operatorPressed = "+"
                buttonAdd.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.red))
                firstNumber = textField.text.toString()
                textField.setText("")
            }
        }
        buttonSubtract.setOnClickListener{
            if(operatorPressed == "" && !lockUntilAC){
                operatorPressed = "-"
                buttonSubtract.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.red))
                firstNumber = textField.text.toString()
                textField.setText("")
            }
        }
        buttonMultiply.setOnClickListener{
            if(operatorPressed == "" && !lockUntilAC){
                operatorPressed = "*"
                buttonMultiply.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.red))
                firstNumber = textField.text.toString()
                textField.setText("")
            }
        }
        buttonDivide.setOnClickListener{
            if (operatorPressed == "" && !lockUntilAC){
                operatorPressed = "/"
                buttonDivide.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.red))
                firstNumber = textField.text.toString()
                textField.setText("")
            }
        }
        buttonSqrt.setOnClickListener{
            if (operatorPressed != "" || lockUntilAC){
                textField.setText(errorMessage)
                lockUntilAC = true
            } else{
                val firstNumberDouble = textField.text.toString().toDouble()
                val result = sqrt(firstNumberDouble)
                textField.setText(result.toString())
            }
        }

        // Equals button listener
        buttonEquals.setOnClickListener{
            if(!lockUntilAC){
                if(operatorPressed != ""){
                    val firstNumberDouble = firstNumber.toDouble()
                    val secondNumberDouble = textField.text.toString().toDouble()

                    when (operatorPressed) {
                        "+" -> {
                            val result = firstNumberDouble + secondNumberDouble
                            textField.setText(result.toString())
                            buttonAdd.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
                        }
                        "-" -> {
                            textField.setText((firstNumberDouble - secondNumberDouble).toString())
                            buttonSubtract.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
                        }
                        "/" -> {
                            if(secondNumberDouble != 0.0){
                                textField.setText((firstNumberDouble / secondNumberDouble).toString())
                            } else{
                                textField.setText(errorMessage)
                                lockUntilAC = true
                            }
                            buttonDivide.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
                        }
                        "*" -> {
                            textField.setText((firstNumberDouble * secondNumberDouble).toString())
                            buttonMultiply.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
                        }
                        else -> {
                            textField.setText(errorMessage)
                        }
                    }

                    // Clearing OperatorPressed after operation
                    operatorPressed = ""

                } else{
                    textField.setText(firstNumber)
                }
            }
        }
    }
}