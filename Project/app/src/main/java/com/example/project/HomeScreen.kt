package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class HomeScreen :AppCompatActivity() {
    // Button Variables
    private lateinit var newEntryButton: Button
    private lateinit var settingsButton: Button

    // RecyclerView Variables
    private lateinit var entriesReycler: RecyclerView

    // TextView Variables
    private lateinit var radiusTextView: TextView

    // Seekbar Variables
    private lateinit var radiusSeekBar: SeekBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        // Setting variables for buttons
        newEntryButton = findViewById(R.id.add_entry_main_screen)
        settingsButton = findViewById(R.id.settings_main_screen)

        // Setting up variable for recycler view
        entriesReycler = findViewById(R.id.entries_main_screen)

        // Setting up variables for seekbar / textview
        radiusTextView = findViewById(R.id.radius_main_screen)
        radiusSeekBar = findViewById(R.id.radius_main_screen)

        // Listeners for buttons
        newEntryButton.setOnClickListener { goToNewEntry() }
        settingsButton.setOnClickListener { goToSettings() }
    }

    private fun goToSettings() {
        // Going to settings activity
        startActivity(Intent(this, Settings::class.java))
    }
    private fun goToNewEntry() {
        // Going to new entry activity
        startActivity(Intent(this, NewEntry::class.java))
    }
}
