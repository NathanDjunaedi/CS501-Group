package com.example.project

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewEntry : AppCompatActivity() {

    // Buttons
    private lateinit var submitNewEntry: Button
    private lateinit var cancelNewEntry: Button
    private lateinit var startTimer: Button

    private lateinit var cars: Spinner

    // TextViews
    private lateinit var quarterTime: TextView
    private lateinit var eighthTime: TextView
    private lateinit var reactionTime: TextView
    private lateinit var eighthMPH: TextView
    private lateinit var quarterMPH: TextView

    // Pictures
    private lateinit var redLight: ImageView
    private lateinit var yellowLight: ImageView
    private lateinit var greenLight: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_entry)

        // Initialize Buttons
        submitNewEntry = findViewById(R.id.submit_new_entry)
        cancelNewEntry = findViewById(R.id.cancel_new_entry)
        startTimer = findViewById(R.id.start_time_new_entry)

        // Initialize Spinner
        cars = findViewById(R.id.vehicle_select_new_entry)
        refreshSpinner()

        // Initialize TextViews
        quarterTime = findViewById(R.id.quarter_time_new_entry)
        eighthTime = findViewById(R.id.eighth_time_new_entry)
        reactionTime = findViewById(R.id.reaction_time_new_entry)
        eighthMPH = findViewById(R.id.eight_mph_new_entry)
        quarterMPH = findViewById(R.id.quarter_mph_new_entry)

        // Initialize Pictures
        redLight = findViewById(R.id.red_image_new_entry)
        yellowLight = findViewById(R.id.yellow_image_new_entry)
        greenLight = findViewById(R.id.green_image_new_entry)

        // Listener for buttons
        submitNewEntry.setOnClickListener {
            addNewEntry()
            finish()
        }


        cancelNewEntry.setOnClickListener {
            finish()
        }


        startTimer.setOnClickListener {
            redLight.setImageResource(R.drawable.red_circle)
            Thread.sleep(1000)

            yellowLight.setImageResource(R.drawable.yellow_circle)
            val randomTime = (1000..2000).random()
            Thread.sleep(randomTime.toLong())

            greenLight.setImageResource(R.drawable.green_circle)

            // Go!

            //TODO: Start recording times



        }
    }

    /*
    ***HELPER FUNCTIONS***
     */

    private fun refreshSpinner() {
        // TODO: Pull new info from DB and refresh currentVehicles spinner






        /*
        example of how to refresh spinner

        val items = listOf("Red", "Green", "Blue")
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, items)
        spinner.setAdapter(adapter)
         */
    }


    /*
    ***DATABASE FUNCTIONS***
     */

    // Add new entry to database
    private fun addNewEntry() {
        var newEntry = Entry(
            id = "TODO",
            poster = "TODO",
            carModel = cars.selectedItem.toString(),
            reactionTime = reactionTime.text.toString().toDouble(),
            eighthMTime = eighthTime.text.toString().toDouble(),
            eighthMSpeed = eighthMPH.text.toString().toDouble(),
            quarterMTime = quarterTime.text.toString().toDouble(),
            quarterMSpeed = quarterMPH.text.toString().toDouble()
        )
        // TODO: Add new entry to database



    }
}