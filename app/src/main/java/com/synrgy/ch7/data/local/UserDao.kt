package com.synrgy.ch7.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.synrgy.ch7.data.local.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    suspend fun getUser(username: String, password: String): User?
}
