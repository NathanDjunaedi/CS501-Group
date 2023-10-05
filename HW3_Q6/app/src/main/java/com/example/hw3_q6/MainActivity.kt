package com.example.hw3_q6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var buttonLogin: android.widget.Button
    private lateinit var loginField: EditText
    private lateinit var passwordField: EditText


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var login = "user"
        var password = "password"

        buttonLogin = findViewById(R.id.buttonLogin)
        loginField = findViewById(R.id.loginField)
        passwordField = findViewById(R.id.passwordField)

        // Login button pressed
        buttonLogin.setOnClickListener{
            if(loginField.text.toString() == login && passwordField.text.toString() == password){
                val welcomeMessage = "Welcome $login"
                Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show()

                // Enter card activity here
                flashcardActivity()

            } else{
                Toast.makeText(this, "Incorrect login!", Toast.LENGTH_SHORT).show()
                loginField.setText("")
                passwordField.setText("")
            }
        }

    }
    private fun flashcardActivity(){
        val intent = CardActivity.newIntent(this@MainActivity)
        startActivity(intent)
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}