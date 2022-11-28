package com.example.tawkto.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tawkto.model.UsersItem

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<UsersItem>)

    // for getting all users from Room database
    @Query("SELECT * FROM user")
    suspend fun getUsers() : List<UsersItem>
}