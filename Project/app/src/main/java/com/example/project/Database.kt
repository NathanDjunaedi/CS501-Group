package com.example.project
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class database: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun entryDao(): EntryDao
    abstract fun raceTimeArrayDao(): RaceTimeArrayDao
}