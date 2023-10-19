package com.example.hw4_q3

import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import kotlin.math.abs
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var lightSwitch: androidx.appcompat.widget.SwitchCompat
    private lateinit var search: SearchView
    private lateinit var flingDetector: GestureDetectorCompat
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraID: String
    private var flashlightState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lightSwitch = findViewById(R.id.lightSwitch)
        search = findViewById(R.id.searchView)

        // flingDetector = GestureDetectorCompat(this, FlingListener())

        // Check if device has a camera
        if(!applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            Toast.makeText(this, "Application Needs Camera!", Toast.LENGTH_LONG).show()
            exitProcess(1)
        }

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraID = cameraManager.cameraIdList[1]

        lightSwitch.setOnClickListener{
            when(lightSwitch.isChecked){
                true -> flashlightOn()
                false -> flashlightOff()
            }
        }
        search.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // Do something when the user changes the text in the SearchView
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                val userQuery = query.lowercase()
                if(userQuery == "on"){
                    flashlightOn()
                } else if (userQuery == "off"){
                    flashlightOff()
                }
                return true
            }
        })
    }

    fun flashlightOn(){
        if(flashlightState){
            Toast.makeText(this, "Flashlight Already On!", Toast.LENGTH_SHORT).show()
        } else{
            cameraManager.setTorchMode(cameraID, true)
            flashlightState = true
            lightSwitch.isChecked = true
        }
    }

    fun flashlightOff(){
        if(!flashlightState){
            Toast.makeText(this, "Flashlight Already Off!", Toast.LENGTH_SHORT).show()
        } else{
            cameraManager.setTorchMode(cameraID, false)
            flashlightState = false
            lightSwitch.isChecked = false
        }
    }

/*
    override fun onTouchEvent(event: MotionEvent): Boolean {
        flingDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }


    inner class FlingListener : GestureDetector.SimpleOnGestureListener() {
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

            if (abs(diffX) < abs(diffY)) {
                // this swipe is a vertical swipe
                if(abs(diffY) > swipeThresh && abs(velocityX) > swipeVelThresh) {
                    if (diffY > 0) {
                        // Up Swipe
                        Log.d(TAG, "Up Swipe")
                        this@MainActivity.flashlightOn()
                    }
                    else {
                        //Down Swipe
                        Log.d(TAG, "Down Swipe")
                        this@MainActivity.flashlightOff()
                    }
                }
            }
            return super.onFling(downEvent, moveEvent, velocityX, velocityY)
        }

     */

}