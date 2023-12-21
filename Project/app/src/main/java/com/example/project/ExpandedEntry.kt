package com.example.project

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExpandedEntry : AppCompatActivity() {

    // Make variables for TextViews
    private lateinit var reactionTime: TextView
    private lateinit var quarterMTime: TextView
    private lateinit var eighthMTime: TextView
    private lateinit var quarterMPH: TextView
    private lateinit var eighthMPH: TextView

    private lateinit var userName: TextView
    private lateinit var vehicle: TextView

    // Variable for buttons
    private lateinit var backButton: Button
    private lateinit var fireButton: Button
    private lateinit var heartButton: Button
    private lateinit var wowButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expanded_entry)

        // Initialize Views
        reactionTime = findViewById(R.id.reaction_time_expanded)
        quarterMTime = findViewById(R.id.quarter_time_expanded)
        eighthMTime = findViewById(R.id.eighth_time_expanded)
        quarterMPH = findViewById(R.id.quarter_mph_expanded)
        eighthMPH = findViewById(R.id.eighth_time_expanded)
        userName = findViewById(R.id.username_expanded)
        vehicle = findViewById(R.id.car_type_expanded)

        // Initialize Buttons
        backButton = findViewById(R.id.cancel_expanded)
        fireButton = findViewById(R.id.reaction_fire_expanded)
        heartButton = findViewById(R.id.reaction_heart_expanded)
        wowButton = findViewById(R.id.reaction_wow_expanded)

        // Storage for which react button was pressed
        var reaction = ""

        // Listener for back button
        backButton.setOnClickListener {
            if (reaction.isNotEmpty()){
                uploadReaction(reaction)
            }
            finish()
        }

        // Listener for fire button
        fireButton.setOnClickListener {
            fireButton.alpha = 1.0f
            heartButton.alpha = 0.5f
            wowButton.alpha = 0.5f
            reaction = "fire"

        }
        // Listener for heart button
        heartButton.setOnClickListener {
            fireButton.alpha = 0.5f
            heartButton.alpha = 1.0f
            wowButton.alpha = 0.5f
            reaction = "heart"
        }
        // Listener for wow button
        wowButton.setOnClickListener {
            fireButton.alpha = 0.5f
            heartButton.alpha = 0.5f
            wowButton.alpha = 1.0f
            reaction = "wow"
        }
    }

    // Function to upload reaction to DB
    private fun uploadReaction(reaction: String) {
        // TODO: Upload reaction to DB




    }

    // Function to get textView information
    private fun getTimes(){
        // Variables to apply
        var reactionTimeVar = ""
        var quarterMTimeVar = ""
        var eighthMTimeVar = ""
        var quarterMPHVar = ""
        var eighthMPHVar = ""
        // TODO: Get times / MPH from DB





        //MPH
        quarterMPH.text = quarterMPHVar
        eighthMPH.text = eighthMPHVar

        //Time
        quarterMTime.text = quarterMTimeVar
        eighthMTime.text = eighthMTimeVar
        reactionTime.text = reactionTimeVar
    }
}