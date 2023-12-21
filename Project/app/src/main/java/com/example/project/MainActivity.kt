package com.example.project

import android.content.Intent
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

    // Variables coming from database
    private lateinit var databaseUser: String
    private lateinit var databasePassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Getting all views available
        userEditText = findViewById<EditText>(R.id.username)
        passwordEditText = findViewById<EditText>(R.id.password)
        loginButton = findViewById<Button>(R.id.login_button)
        registerButton = findViewById<Button>(R.id.registerButton)



        // Setting the on click listener for the login button
        loginButton.setOnClickListener {

        }
    }
    // Singleton class to hold username
    object Username {
        var username: String? = null
    }
    // Login method
    private fun login() {
        // Getting the user input
        userName = userEditText.text.toString()
        password = passwordEditText.text.toString()

        // Checking if the user input is empty
        if (userName.isEmpty() || password.isEmpty()) {
            // Displaying an error message
            userEditText.error = "Username is required"
            passwordEditText.error = "Password is required"
        } else {
            val userDao = SandSDatabase.getDatabase(applicationContext).userDao()
            val user = userDao.getUser(userName)


            // Checking if the user input is correct
            if (userName == "admin" && password == "admin") {
                Username.username = "admin"
                // Displaying a success message
                userEditText.error = "Login Successful"
                passwordEditText.error = "Login Successful"
            }
            else if (user.password == password) {
                Username.username = userName
                userEditText.error = "Login Successful"
                passwordEditText.error = "Login Successful"
            }
            else {
                // Displaying an error message
                userEditText.error = "Invalid username or password"
                passwordEditText.error = "Invalid username or password"
            }
        }
    }
    // Method for going to register activity
    private fun registration() {
        // Going to register activity
        startActivity(Intent(this, Registration::class.java))
    }
}