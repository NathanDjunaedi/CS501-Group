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
    @Query("SELECT * FROM users WHERE username = :username")
    fun getUser(username: String): User
    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    fun usernameExists(username: String): Int
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
    fun getAllEntries(): List<Entry>

    @Update
    suspend fun updateEntry(entry: Entry)

    @Delete
    suspend fun deleteEntry(entry: Entry)

    @Query("DELETE FROM entries")
    suspend fun deleteAll()
}


