package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize gui elements as variables to let us access them later
        val spinner: Spinner = findViewById(R.id.spinner_operand)
        val edittextOne: EditText = findViewById(R.id.number_input)
        val edittextTwo: EditText = findViewById(R.id.number_input_2)
        val textView = findViewById<TextView>(R.id.textview1)

        // Adapter to let us set the spinner's contents
        ArrayAdapter.createFromResource(
            this,
            R.array.operands,
            android.R.layout.simple_spinner_item
        ).also {adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // Listener class to let us grab whatever operand the spinner is set to
        class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                parent.getItemAtPosition(pos)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        // Set the operand spinner's listener to an instance of above listener
        val operandListener = SpinnerActivity()
        spinner.onItemSelectedListener = operandListener

        // Define a listener for when the calculate button is clicked
        findViewById<Button>(R.id.calculate_button).setOnClickListener {
            // Grab the values of the operand spinner and the two edittext fields
            val operand = spinner.selectedItem.toString()
            val numOne = edittextOne.text.toString().toDouble()
            val numTwo = edittextTwo.text.toString().toDouble()
            var result = 0.0
            var error = ""

            // Pattern match to apply the operand to the two entered numbers
            when(operand) {
                "Addition" -> result = numOne + numTwo
                "Subtraction" -> result = numOne - numTwo
                "Multiplication" -> result = numOne * numTwo
                "Division" -> {
                    // Divide by zero error
                    if(numTwo != 0.0)
                        {result = numOne / numTwo}
                    else
                        error = "Cannot divide by 0"
                }
                "Modulus" -> {
                    // Check that numTwo != 0 and both numbers are integers
                    if((numOne % 1.0 == 0.0) and (numTwo % 1.0 == 0.0))
                        result = numOne % numTwo
                        error = if(numTwo == 0.0)
                            "Cannot modulo by 0"
                    else
                        "Cannot modulo two non-integers"
                }
            }
            // Change the textview string to the result of the operation
            if (error == "") textView.text = String.format("%.3f", result)
            // If there is an error, print the error instead
            else textView.text = error
        }
    }
}