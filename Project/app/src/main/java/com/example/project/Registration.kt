package com.example.project

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Registration : AppCompatActivity() {

    // Listing all components in the layout
    private lateinit var newUsername: EditText
    private lateinit var newPassword: EditText
    private lateinit var year: AutoCompleteTextView
    private lateinit var make: AutoCompleteTextView
    private lateinit var model: AutoCompleteTextView
    private lateinit var register: Button
    private lateinit var back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        // Initializing all components
        newUsername = findViewById(R.id.username_edittext)
        newPassword = findViewById(R.id.password_edittext)

        year = findViewById(R.id.car_year_autocomplete_registration)
        make = findViewById(R.id.car_make_autocomplete_registration)
        model = findViewById(R.id.car_model_autocomplete_registration)

        register = findViewById(R.id.register_button)
        back = findViewById(R.id.cancel_registration)

        // Initializing the autocomplete textviews
        val years = resources.getStringArray(R.array.years)
        val makes = resources.getStringArray(R.array.makes)
        val models = resources.getStringArray(R.array.models)

        // Setting up the autocomplete textviews
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, years)
        year.setAdapter(adapter)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, makes)
        make.setAdapter(adapter2)
        val adapter3 = ArrayAdapter(this, android.R.layout.simple_list_item_1, models)
        model.setAdapter(adapter3)

        // Listener for register button
        register.setOnClickListener {
            verifyRegistration()
        }

        // Listener for back button
        back.setOnClickListener {
            finish()
        }
    }

    private fun verifyRegistration() {
        // Get edittext values
        val username = newUsername.text.toString()
        val password = newPassword.text.toString()
        val year = year.text.toString()
        val make = make.text.toString()
        val model = model.text.toString()

        // Verify that none are empty
        if (username.isNotEmpty() && password.isNotEmpty() && year.isNotEmpty() && make.isNotEmpty() && model.isNotEmpty()) {
            // Upload to database and exit registration
            // TODO: Add to database
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            // Display error message
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }
}