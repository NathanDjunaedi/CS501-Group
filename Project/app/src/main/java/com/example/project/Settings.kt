package com.example.project

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class Settings : AppCompatActivity() {
    // Get all elements from the layout
    private lateinit var oldPassword: EditText
    private lateinit var newPassword: EditText

    private lateinit var currentVehicles: Spinner

    private lateinit var newVehicleYear: AutoCompleteTextView
    private lateinit var newVehicleMake: AutoCompleteTextView
    private lateinit var newVehicleModel: AutoCompleteTextView

    private lateinit var addVehicleButton: Button
    private lateinit var changePasswordButton: Button
    private lateinit var backButton: Button
    private lateinit var deleteVehicleButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        // Finding all views
        changePasswordButton = findViewById(R.id.change_password_button)
        backButton = findViewById(R.id.cancel_button_settings)
        addVehicleButton = findViewById(R.id.settings_add_vehicle_button)
        deleteVehicleButton = findViewById(R.id.delete_car_settings)

        oldPassword = findViewById(R.id.old_password_edittext)
        newPassword = findViewById(R.id.new_password_edittext)

        currentVehicles = findViewById(R.id.vehicle_select_settings)

        newVehicleYear = findViewById(R.id.car_year_autocomplete_settings)
        newVehicleMake = findViewById(R.id.car_make_autocomplete_settings)
        newVehicleModel = findViewById(R.id.car_model_autocomplete_settings)

        // Initializing the autocomplete textviews
        val years = resources.getStringArray(R.array.years)
        val makes = resources.getStringArray(R.array.makes)
        val models = resources.getStringArray(R.array.models)

        // Setting up the autocomplete textviews
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, years)
        newVehicleYear.setAdapter(adapter)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, makes)
        newVehicleMake.setAdapter(adapter2)
        val adapter3 = ArrayAdapter(this, android.R.layout.simple_list_item_1, models)
        newVehicleModel.setAdapter(adapter3)

        // Populate spinner with current cars
        refreshSpinner()

        /*
        ***ON CLICK LISTENERS***
         */

        addVehicleButton.setOnClickListener {
            // Check if all fields are populated
            if (checkNewVehicle()) {
                // Call method to add vehicle to DB
                addVehicleToDB(concatenateCar())
                refreshSpinner()
            } else {
                // If all fields are not populated, then notify user
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        changePasswordButton.setOnClickListener {
            // Check if password fields are populated
            if (!checkPasswordEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            } else if (!checkOldPassword()) {
                // Incorrect password, notify and clear fields
                Toast.makeText(this, "Incorrect old password!", Toast.LENGTH_SHORT).show()
                oldPassword.text.clear()
                newPassword.text.clear()
                return@setOnClickListener
            }
            changePasswordInDB()
        }

        deleteVehicleButton.setOnClickListener {
            // Listener for delete vehicle button

            // Get currently selected element in spinner and call delete method
            val selectedCar = currentVehicles.selectedItem.toString()
            deleteVehicleFromDB(selectedCar)

            // Refresh spinner with new information
            refreshSpinner()
        }

        backButton.setOnClickListener {
            // Listener for back button
            finish()
        }
    }

    /*
    ***HELPER METHODS***
     */

    private fun concatenateCar(): String {
        // Function to concatenate car values to one string
        return newVehicleYear.text.toString() + " " + newVehicleMake.text.toString() + " " + newVehicleModel.text.toString()
    }

    private fun checkNewVehicle(): Boolean {
        // Function to make sure new vehicle fields are populated
        return newVehicleYear.text.isNotEmpty() && newVehicleMake.text.isNotEmpty() && newVehicleModel.text.isNotEmpty()
    }

    private fun checkPasswordEmpty(): Boolean {
        // Function to make sure password fields are populated
        return oldPassword.text.isNotEmpty() && newPassword.text.isNotEmpty()
    }

    /*
    ***DATABASE METHODS***
     */


    // Function to check if the old password is correct
    private fun checkOldPassword(): Boolean {
        val database = SandSDatabase.getDatabase(context = applicationContext)
        val userDao = database.userDao()

        // Pull current password from DB
        var oldPasswordDB: String? = null
        if (MainActivity.Username.username != null) {
            val user = userDao.getUser(username = MainActivity.Username.username!!)
            oldPasswordDB = user.password
        }

        return oldPasswordDB == oldPassword.text.toString()

    }

    // Method to refresh spinner with new information
    private fun refreshSpinner() {
        val database = SandSDatabase.getDatabase(context = applicationContext)
        val userDao = database.userDao()

        if (MainActivity.Username.username != null) {
            val user = userDao.getUser(username = MainActivity.Username.username!!)
            val carList = user.cars.toList()
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, carList)
        }
    }

    // Method to add vehicle to database
    private fun addVehicleToDB(car: String) {
        // Add vehicle to User in DB
        val database = SandSDatabase.getDatabase(context = applicationContext)
        val userDao = database.userDao()

        if (MainActivity.Username.username != null) {
            val user = userDao.getUser(username = MainActivity.Username.username!!)
            user.cars = user.cars + listOf(car)
            val scope = CoroutineScope(Dispatchers.Default)
            scope.launch {
                userDao.updateUser(user)
            }
            scope.cancel()
        }

        Toast.makeText(this, "Vehicle added successfully!", Toast.LENGTH_SHORT).show()
        newVehicleYear.text.clear()
        newVehicleMake.text.clear()
        newVehicleModel.text.clear()
    }

    // Method to delete vehicle from database
    private fun deleteVehicleFromDB(car: String) {
        val database = SandSDatabase.getDatabase(context = applicationContext)
        val userDao = database.userDao()

        if (MainActivity.Username.username != null) {
            val user = userDao.getUser(username = MainActivity.Username.username!!)
            val scope = CoroutineScope(Dispatchers.Default)
            val cars = user.cars.toMutableList()

            cars.remove(car)
            user.cars = cars

            scope.launch {
                userDao.updateUser(user)
            }
            scope.cancel()
        }
        Toast.makeText(this, "Vehicle deleted successfully!", Toast.LENGTH_SHORT).show()
    }

    // Method to change password in DB
    private fun changePasswordInDB() {
        val database = SandSDatabase.getDatabase(context = applicationContext)
        val userDao = database.userDao()

        if (MainActivity.Username.username != null) {
            val user = userDao.getUser(username = MainActivity.Username.username!!)
            val scope = CoroutineScope(Dispatchers.Default)
            val password = user.password
            if (oldPassword.text.toString() == password) {
                user.password = newPassword.text.toString()

                scope.launch {
                    userDao.updateUser(user)
                }
                scope.cancel()
                Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show()
                oldPassword.text.clear()
                newPassword.text.clear()
            }
            else {
                Toast.makeText(this, "Old password did not match!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
