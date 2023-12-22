package com.example.project

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "users")
data class User(
    @PrimaryKey var username: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "cars") var cars: String
)


@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "poster") var poster: String,
    @ColumnInfo(name = "carModel") var carModel: String,
    @ColumnInfo(name = "reactionTime") var reactionTime: Double,
    @ColumnInfo(name = "eighthMTime") var eighthMTime: Double,
    @ColumnInfo(name = "eighthMSpeed") var eighthMSpeed: Double,
    @ColumnInfo(name = "quarterMTime") var quarterMTime: Double,
    @ColumnInfo(name = "quarterMSpeed") var quarterMSpeed: Double
)

