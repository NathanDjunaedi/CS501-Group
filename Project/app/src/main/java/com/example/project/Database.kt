package com.example.project
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class SandSDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun entryDao(): EntryDao
    abstract fun raceTimeArrayDao(): RaceTimeArrayDao
    companion object {
        fun getDatabase(context: Context): SandSDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                SandSDatabase::class.java,
                "SandSDatabase"
            ).build()
        }
    }
}

class DatabaseManager private constructor(context: Context) {
    private val database: SandSDatabase = Room.databaseBuilder(
        context.applicationContext,
        SandSDatabase::class.java,
        "SandS_database"
    ).build()
    companion object {
        @Volatile
        private var INSTANCE: DatabaseManager? = null

        fun getInstance(context: Context): DatabaseManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DatabaseManager(context).also { INSTANCE = it }
            }
        }
    }
}