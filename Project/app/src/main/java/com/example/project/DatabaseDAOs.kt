package com.example.project
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    suspend fun insert(entity: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user:User)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}

@Dao
interface EntryDao {
    @Insert
    suspend fun insert(entity: Entry)

    @Query("SELECT * FROM entries")
    fun getAllEntries()

    @Update
    suspend fun updateEntry(entry: Entry)

    @Delete
    suspend fun deleteEntry(entry: Entry)

    @Query("DELETE FROM entries")
    suspend fun deleteAll()
}

@Dao
interface RaceTimeArrayDao {
    @Insert
    suspend fun insert(timeArray: RaceTimeArray)

    @Query("SELECT * FROM raceTimeArrays")
    fun getAllEntries()

    @Update
    suspend fun updateArray(timeArray: RaceTimeArray)

    @Delete
    suspend fun deleteArray(timeArray: RaceTimeArray)

    @Query("DELETE FROM raceTimeArrays")
    suspend fun deleteAll()
}

@Dao
interface RaceTimeDao {
    @Insert
    suspend fun insert(timeArray: RaceTimeArray)

    @Query("SELECT * FROM raceTimeArrays")
    fun getAllEntries()

    @Update
    suspend fun updateTime(raceTime: RaceTime)

    @Delete
    suspend fun deleteTime(raceTime: RaceTime)

    @Query("DELETE FROM raceTime")
    suspend fun deleteAll()
}
