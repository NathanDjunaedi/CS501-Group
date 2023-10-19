package com.example.hw4_q2

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import java.util.Objects
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var sensitivityBar: SeekBar
    private lateinit var textView: TextView
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensitivityBar = findViewById(R.id.sensitivityBar)
        textView = findViewById(R.id.textView)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Objects.requireNonNull(sensorManager).registerListener(sensorListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
    }

    private val sensorListener: SensorEventListener = object : SensorEventListener{
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val gravity = 9.81
            lastAcceleration = currentAcceleration
            val accelerationX = abs(x)
            val accelerationY = abs(y - gravity).toFloat()
            val accelerationZ = abs(z)

            val maxAxis = Math.max(accelerationX, Math.max(accelerationY, accelerationZ))

            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val xToast = Toast.makeText(this@MainActivity, "X Axis!", Toast.LENGTH_SHORT)
            val yToast = Toast.makeText(this@MainActivity, "Y Axis!", Toast.LENGTH_SHORT)
            val zToast = Toast.makeText(this@MainActivity, "Z Axis!", Toast.LENGTH_SHORT)

            if(maxAxis > (sensitivityBar.progress + 1) * 2){

                when (maxAxis){
                    accelerationX -> {
                        Log.d(TAG, "X Axis")
                        xToast.show()
                    }
                    accelerationY -> {
                        Log.d(TAG, "Y Axis")
                        yToast.show()
                    }
                    accelerationZ -> {
                        Log.d(TAG, "Z Axis")
                        zToast.show()
                        }
                    else -> Log.d(TAG, "Error!")
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        }
    }
}