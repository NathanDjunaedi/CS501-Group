package com.example.project
import android.content.Context
import androidx.room.*

@Database(entities = [User::class, Entry::class], version = 1)
abstract class SandSDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun entryDao(): EntryDao
}
