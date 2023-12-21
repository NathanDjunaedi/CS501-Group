package com.example.project

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "users")
data class User(
    @PrimaryKey var username: String,
    var password: String,
    var cars: List<String>
)


@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey val id: String,
    var poster: String,
    var carModel: String,
    var reactionTime: Double,
    var eighthMTime: Double,
    var eighthMSpeed: Double,
    var quarterMTime: Double,
    var quarterMSpeed: Double
)

@Entity(tableName = "raceTimeArrays")
data class RaceTimeArray(
    @PrimaryKey val tableId: String,
    var entryId: String,
    var raceTimeId: UUID
)
