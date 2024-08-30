package com.synrgy.ch7.data.repository

import com.synrgy.ch7.data.local.model.User

interface UserRepository {
    suspend fun register(user: User): Boolean
    suspend fun login(username: String, password: String): Boolean
}
