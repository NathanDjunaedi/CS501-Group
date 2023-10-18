package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.GestureDetectorCompat
import java.util.Objects
import kotlin.math.abs
import kotlin.math.sqrt

private const val TAG = "HOME"

class Home : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var mDetector: GestureDetectorCompat
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Objects.requireNonNull(sensorManager).registerListener(sensorListener, sensorManager
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        mDetector = GestureDetectorCompat(this, MyFlingListener())
    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta
            if (abs(acceleration) > 12) {
                Log.d(TAG, "Shaken")
                val shake = AnimationUtils.loadAnimation(applicationContext, R.anim.shake)
                val image: ImageView = findViewById(R.id.homeImage)
                image.startAnimation(shake)
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    fun onSwipeUp() {
        val northIntent = Intent(this, North::class.java)
        sensorManager.unregisterListener(sensorListener)
        startActivity(northIntent)
    }

    fun onSwipeDown() {
        val southIntent = Intent(this, South::class.java)
        sensorManager.unregisterListener(sensorListener)
        startActivity(southIntent)
    }

    fun onSwipeLeft() {
        val westIntent = Intent(this, West::class.java)
        sensorManager.unregisterListener(sensorListener)
        startActivity(westIntent)
    }

    fun onSwipeRight() {
        val eastIntent = Intent(this, East::class.java)
        sensorManager.unregisterListener(sensorListener)
        startActivity(eastIntent)
    }

    inner class MyFlingListener : GestureDetector.SimpleOnGestureListener() {
        // listener class for flings
        private val swipeThresh = 100
        private val swipeVelThresh = 100

        override fun onFling(
            // overriding onFling events only
            downEvent: MotionEvent,
            moveEvent: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val diffX = moveEvent.x.minus(downEvent.x)
            val diffY = moveEvent.y.minus(downEvent.y)

            if (abs(diffX) > abs(diffY)) {
                // this swipe is a horizontal swipe
                if(abs(diffX) > swipeThresh && abs(velocityX) > swipeVelThresh) {
                    if (diffX < 0) {
                        // right swipe
                        Log.d(TAG, "Right Swipe")
                        this@Home.onSwipeRight()
                    }
                    else {
                        //left swipe
                        Log.d(TAG, "Left Swipe")
                        this@Home.onSwipeLeft()
                    }
                }
            }
            else {
                // this swipe is a vertical swipe
                if(abs(diffY) > swipeThresh && abs(velocityX) > swipeVelThresh) {
                    if (diffY > 0) {
                        // up swipe
                        Log.d(TAG, "Up Swipe")
                        this@Home.onSwipeUp()
                    }
                    else {
                        //down swipe
                        Log.d(TAG, "Down Swipe")
                        this@Home.onSwipeDown()
                    }
                }
            }
            return super.onFling(downEvent, moveEvent, velocityX, velocityY)
        }
    }
    override fun onResume() {
        Log.d(TAG, "onResume Called")
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(
            Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }
}