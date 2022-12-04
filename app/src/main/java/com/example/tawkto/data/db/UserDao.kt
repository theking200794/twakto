package com.example.tawkto.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tawkto.model.UsersItem
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // for adding users list into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<UsersItem>)

    // for getting all users from Room database
    @Query("SELECT * FROM user")
    suspend fun getUsers() : List<UsersItem>

    //filtering data as user type in search bar
    @Query("SELECT * FROM user WHERE login LIKE :query")
    fun searchLocalDatabase(query: String): Flow<List<UsersItem>>
}