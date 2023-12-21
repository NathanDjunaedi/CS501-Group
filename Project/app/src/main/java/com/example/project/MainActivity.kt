package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var userName: String
    private lateinit var password: String
    private lateinit var userEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var fileUser: String
    private lateinit var filePassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Getting all views available
        userEditText = findViewById<EditText>(R.id.username)
        passwordEditText = findViewById<EditText>(R.id.password)
        loginButton = findViewById<Button>(R.id.login_button)
        registerButton = findViewById<Button>(R.id.registerButton)

        // Getting the user input
        userName = userEditText.text.toString()
        password = passwordEditText.text.toString()

        // Calling the

        // Setting the on click listener for the login button
        loginButton.setOnClickListener {
            // Checking if the user input is empty
            if (userName.isEmpty() || password.isEmpty()) {
                // Displaying an error message
                userEditText.error = "Username is required"
                passwordEditText.error = "Password is required"
            } else {
                // Checking if the user input is correct
                if (userName == "admin" && password == "admin") {
                    // Displaying a success message
                    userEditText.error = "Login Successful"
                    passwordEditText.error = "Login Successful"
                } else if (userName == fileUser && password == filePassword) {
                    //Display success message
                    userEditText.error = "Login Successful"
                    passwordEditText.error = "Login Successful"
                }else {
                    // Displaying an error message
                    userEditText.error = "Invalid username or password"
                    passwordEditText.error = "Invalid username or password"
                }
            }
        }


    }
}