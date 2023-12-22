package com.example.project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.Default)
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
            login()
        }

        // Setting the on click listener for the register button
        registerButton.setOnClickListener {
            goToRegistration()
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
        if (!checkLogin()) {
            // Displaying an error message
            userEditText.error = "Username is required"
            passwordEditText.error = "Password is required"
            return
        }

        // Getting the user from the database
        val userDao = Room.databaseBuilder(
            applicationContext,
            SandSDatabase::class.java,
            "SandSDatabase"
        ).build().userDao()
        var user: User? = null
        scope.launch {
            val user1: User = userDao.getUser(userName)
            user = user1
        }
        scope.cancel()
        // FOR PRESENTATION PURPOSES ONLY
        if (userName == "admin" && password == "admin") {
            Username.username = "admin"
            // Displaying a success message
            userEditText.error = "Login Successful"
            passwordEditText.error = "Login Successful"
            goToHome()
        }
        else if (user?.password  == password) {
            Username.username = userName
            userEditText.error = "Login Successful"
            passwordEditText.error = "Login Successful"
            goToHome()
        }
        else {
            // Displaying an error message
            userEditText.error = "Invalid username or password"
            passwordEditText.error = "Invalid username or password"
        }
    }

    private fun checkLogin(): Boolean {
        // Checking if the login is populated
        return userEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
    }

    private fun goToRegistration() {
        // Going to register activity
        startActivity(Intent(this, Registration::class.java))
    }
    private fun goToHome() {
        // Going to home activity
        startActivity(Intent(this, HomeScreen::class.java))
    }
}