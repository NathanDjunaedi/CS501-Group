package com.example.project

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    val username: String,
    val password: String,
    val cars: List<String>


@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey val id: String,
    val poster: String,
    val carModel: String,
    val reactionTime: Double,
    val eighthMTime: Double,
    val eighthMSpeed: Double,
    val quarterMTime: Double,
    val quarterMSpeed: Double
)

@Entity(tableName = "raceTimeArrays")
data class RaceTimeArray(
    @PrimaryKey val tableId: String,
    val entryId: UUID,
    val raceTimeId: UUID
)
