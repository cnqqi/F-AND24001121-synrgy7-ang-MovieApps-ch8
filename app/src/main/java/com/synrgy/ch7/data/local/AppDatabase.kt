package com.synrgy.ch7.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.synrgy.ch7.data.local.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
