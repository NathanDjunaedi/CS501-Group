package com.example.project

import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.UUID
import kotlin.math.abs
import kotlin.math.sqrt

class NewEntry : AppCompatActivity() {

    // Buttons
    private lateinit var submitNewEntry: Button
    private lateinit var cancelNewEntry: Button
    private lateinit var startTimer: Button

    private lateinit var cars: Spinner

    // TextViews
    private lateinit var quarterTime: TextView
    private lateinit var eighthTime: TextView
    private lateinit var reactionTimeText: TextView
    private lateinit var eighthMPH: TextView
    private lateinit var quarterMPH: TextView

    // Pictures
    private lateinit var redLight: ImageView
    private lateinit var yellowLight: ImageView
    private lateinit var greenLight: ImageView

    // Location listeners
    private lateinit var  locationManager: LocationManager
    private lateinit var  startLocManager: LocationManager

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
        reactionTimeText = findViewById(R.id.reaction_time_new_entry)
        eighthMPH = findViewById(R.id.eight_mph_new_entry)
        quarterMPH = findViewById(R.id.quarter_mph_new_entry)

        // Initialize Pictures
        redLight = findViewById(R.id.red_image_new_entry)
        yellowLight = findViewById(R.id.yellow_image_new_entry)
        greenLight = findViewById(R.id.green_image_new_entry)

        // Listener for buttons
        submitNewEntry.setOnClickListener {
            finish()
        }


        cancelNewEntry.setOnClickListener {
            finish()
        }

        //Location listener
        //Checks if location perms available, asks for it if not.
        if (ContextCompat.checkSelfPermission(
                this,
                "android.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf("android.permission.ACCESS_FINE_LOCATION"),
                1
            )
        }

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        startLocManager = getSystemService(LOCATION_SERVICE) as LocationManager

        class StartLoc : LocationListener {
            val startLoc = locationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER,)
            override fun onLocationChanged(location: Location) {
                    if (startLoc != location) {
                        Toast.makeText(this@NewEntry, "Too Early!", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                }
            }


        class SpeedData {
            var topSpeed: Float = 0f
            val startTime: Long = System.currentTimeMillis()
            var eighthTime = System.currentTimeMillis()
            var quarterTime = System.currentTimeMillis()
        }

        class CLoc(id: UUID) : LocationListener {
            // location listener for times and speeds
            val idval = id
            var entry: SpeedData = SpeedData()
            val startLoc = locationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER,)
            override fun onLocationChanged(location: Location) {
                val database = SandSDatabase.getDatabase(context = applicationContext)
                val entryDao = database.entryDao()
                val scope = CoroutineScope(Dispatchers.Default)
                val currentSpeed = location.speed * 2.23694f
                if (currentSpeed > entry.topSpeed) {
                    entry.topSpeed = currentSpeed
                }
                if (location.distanceTo(startLoc!!).toDouble() == 201.168) {
                    entry.eighthTime = System.currentTimeMillis() - entry.startTime
                }
                if (location.distanceTo(startLoc).toDouble() == 402.336) {
                    entry.quarterTime = System.currentTimeMillis() - entry.startTime

                    val entryResult = Entry(
                        id = idval,
                        poster = MainActivity.Username.username!!,
                        carModel = cars.toString(),
                        eighthMSpeed = ( (201.168.toLong() / entry.eighthTime) * 2.23694f).toDouble(),
                        eighthMTime = entry.eighthTime.toDouble(),
                        quarterMSpeed = ( (402.336.toLong() / entry.quarterTime) * 2.23694f).toDouble(),
                        quarterMTime = entry.quarterTime.toDouble(),
                        reactionTime = 0.0
                    )
                    scope.launch {
                        entryDao.insert(entryResult)
                    }
                    scope.cancel()
                    stopLocationUpdates()
                }
            }
            fun stopLocationUpdates() {
                // Stop location updates
                locationManager.removeUpdates(this)
            }
        }

        val sLocListener = StartLoc()


        // On Start
        startTimer.setOnClickListener {
            val database = SandSDatabase.getDatabase(context = applicationContext)
            val entryDao = database.entryDao()
            val scope = CoroutineScope(Dispatchers.Default)
            val sessUID = java.util.UUID.randomUUID()
            val startLoc = locationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER,)

            //Listen for movement before start, end if there is.
            startLocManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                sLocListener
            )
            redLight.setImageResource(R.drawable.red_circle)
            Thread.sleep(1000)

            yellowLight.setImageResource(R.drawable.yellow_circle)
            val randomTime = (1000..2000).random()
            Thread.sleep(randomTime.toLong())

            greenLight.setImageResource(R.drawable.green_circle)
            //initialize start & end time for reaction
            val startTime = System.currentTimeMillis()
            var endTime = System.currentTimeMillis()
            var reactionTime: Long = 0L


            // Go!
            startLocManager.removeUpdates(sLocListener)

            val sensorListener: SensorEventListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent) {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]
                    // Measure all 3 so that phone orientation doesn't affect it
                    val avAccel = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                    if (abs(avAccel) > 2) {
                        endTime = System.currentTimeMillis()
                        reactionTime = endTime - startTime
                    }
                }
                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
            }

            val cloc = CLoc(sessUID)
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                cloc
            )
            val CEntry = entryDao.getEntry(sessUID)
            CEntry.reactionTime = reactionTime.toDouble()
            quarterTime.text = CEntry.quarterMTime.toString()
            quarterMPH.text = CEntry.quarterMSpeed.toString()
            eighthTime.text = CEntry.eighthMTime.toString()
            eighthMPH.text = CEntry.eighthMSpeed.toString()
            reactionTimeText.text = CEntry.reactionTime.toString()
            scope.launch {
                entryDao.updateEntry(CEntry)
            }
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

    // Add new entry to database (DEPRECIATED - I accidentally wrote it into the listeners - Nathan)
//    private fun addNewEntry() {
//        var newEntry = Entry(
//            poster =
//            carModel = cars.selectedItem.toString(),
//            reactionTime = reactionTime.text.toString().toDouble(),
//            eighthMTime = eighthTime.text.toString().toDouble(),
//            eighthMSpeed = eighthMPH.text.toString().toDouble(),
//            quarterMTime = quarterTime.text.toString().toDouble(),
//            quarterMSpeed = quarterMPH.text.toString().toDouble()
//        )
}