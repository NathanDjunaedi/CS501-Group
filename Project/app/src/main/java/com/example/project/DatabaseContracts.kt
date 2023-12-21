package com.example.project

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    val username: String,
    val email: String,
    val carModel: Int,
    val profileImage: Image
)

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val location: String,
)

@Entity(tableName = "raceTimeArrays")
data class RaceTimeArray(
    @PrimaryKey val tableId: String,
    val entryId: UUID,
    val raceTimeId: UUID
)

@Entity(tableName = "raceTime")
data class RaceTime(
    @PrimaryKey val raceId: String,
    val racerId: String,
    val raceTime: Double
)
