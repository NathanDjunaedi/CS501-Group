package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

class HomeScreen :AppCompatActivity() {
    // Button Variables
    private lateinit var newEntryButton: Button
    private lateinit var settingsButton: Button
    private lateinit var expandEntry: Button

    // RecyclerView Variables
    private lateinit var entriesSpinner: Spinner

    // TextView Variables
    private lateinit var radiusNumber: TextView

    // Seekbar Variables
    private lateinit var radiusSeekBar: SeekBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        // Setting variables for buttons
        newEntryButton = findViewById(R.id.add_entry_main_screen)
        settingsButton = findViewById(R.id.settings_main_screen)
        expandEntry = findViewById(R.id.expand_entry_main_screen)


        // Setting up variable for entries spinner
        entriesSpinner = findViewById(R.id.entries_spinner)
        refreshSpinner()

        // Setting up variables for seekbar / textview
        radiusNumber = findViewById(R.id.radius_number_main_screen)
        radiusSeekBar = findViewById(R.id.radius_main_screen)

        // Listeners for buttons
        newEntryButton.setOnClickListener { goToNewEntry() }
        settingsButton.setOnClickListener { goToSettings() }
        expandEntry.setOnClickListener { goToExpandEntry() }

    }

    private fun refreshSpinner() {
        TODO("Not yet implemented")
    }

    private fun goToSettings() {
        // Going to settings activity
        startActivity(Intent(this, Settings::class.java))
    }

    private fun goToNewEntry() {
        // Going to new entry activity
        startActivity(Intent(this, NewEntry::class.java))
    }

    private fun goToExpandEntry() {
        // Going to expand entry activity
        val selectedEntry = entriesSpinner.selectedItem.toString()
        val intent = Intent(this, ExpandedEntry::class.java)
        intent.putExtra("selectedEntry", selectedEntry)
    }
}