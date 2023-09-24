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
//        textField.isActivated = true
//        textField.isPressed = true

        var currentText: String
        var operatorPressedBool = false
        var firstnumber = ""
        var operatorPressed = ""

        // Listeners for all of the number buttons
        button0.setOnClickListener{
            currentText = textField.text.toString() + "0"
            textField.setText(currentText)
        }
        button1.setOnClickListener{
            currentText = textField.text.toString() + "1"
            textField.setText(currentText)
        }
        button2.setOnClickListener{
            currentText = textField.text.toString() + "2"
            textField.setText(currentText)
        }
        button3.setOnClickListener{
            currentText = textField.text.toString() + "3"
            textField.setText(currentText)
        }
        button4.setOnClickListener{
            currentText = textField.text.toString() + "4"
            textField.setText(currentText)
        }
        button5.setOnClickListener{
            currentText = textField.text.toString() + "5"
            textField.setText(currentText)
        }
        button6.setOnClickListener{
            currentText = textField.text.toString() + "6"
            textField.setText(currentText)
        }
        button7.setOnClickListener{
            currentText = textField.text.toString() + "7"
            textField.setText(currentText)
        }
        button8.setOnClickListener{
            currentText = textField.text.toString() + "8"
            textField.setText(currentText)
        }
        button9.setOnClickListener{
            currentText = textField.text.toString() + "9"
            textField.setText(currentText)
        }
        buttonDecimal.setOnClickListener{
            if (!textField.text.toString().contains(".")) {
                currentText = textField.text.toString() + "."
                textField.setText(currentText)
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
            firstnumber = null.toString()
        }

        // Listeners for operations
        buttonAdd.setOnClickListener{
            if (operatorPressed == ""){
                operatorPressed = "+"
                buttonAdd.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.red))
                firstnumber = textField.toString()
                textField.setText("")
            }
        }
        buttonSubtract.setOnClickListener{
            if(operatorPressed == ""){
                operatorPressed = "-"
                buttonSubtract.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.red))
                firstnumber = textField.toString()
                textField.setText("")
            }
        }
        buttonMultiply.setOnClickListener{
            if(operatorPressed == ""){
                operatorPressed = "*"
                buttonMultiply.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.red))
                firstnumber = textField.toString()
                textField.setText("")
            }
        }
        buttonDivide.setOnClickListener{
            if (operatorPressed == ""){
                operatorPressed = "/"
                buttonDivide.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.red))
                firstnumber = textField.toString()
                textField.setText("")
            }
        }
        buttonSqrt.setOnClickListener{

        }

        // Equals button listener
        buttonEquals.setOnClickListener{
            if(operatorPressed != ""){
                var firstNumberDouble = textField.text.toString().toDouble()
                var secondNumberDouble = textField.text.toString().toDouble()

                if(operatorPressed == "+"){
                    var result = firstNumberDouble + secondNumberDouble
                    textField.setText(result.toString())
                    buttonAdd.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
                } else if (operatorPressed == "-"){
//                    textField.setText((firstNumberDouble - secondNumberDouble).toString())
                    textField.setText("test")
                    buttonSubtract.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
                } else if(operatorPressed == "/"){
                    textField.setText((firstNumberDouble / secondNumberDouble).toString())
                    buttonDivide.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
                } else if(operatorPressed == "*"){
                    textField.setText((firstNumberDouble * secondNumberDouble).toString())
                    buttonMultiply.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
                } else{
                    textField.setText("ERROR: PRESS AC")
                }

                // Clearing OperatorPressed after operation
                operatorPressed = ""


                /*
                TODO: FINISH IMPLEMENTING EQUALS AND ADD FUNCTIONALITY FOR SQUARE ROOT
                 */


            }
        }



    }



    // Creating button listeners to concatenate with the current text


}