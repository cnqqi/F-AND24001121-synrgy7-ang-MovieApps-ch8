package com.synrgy.ch7.data.repository

import com.synrgy.ch7.data.local.PreferencesManager
import com.synrgy.ch7.data.local.UserDao
import com.synrgy.ch7.data.local.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val preferencesManager: PreferencesManager
) : UserRepository {

    override suspend fun register(user: User): Boolean {
        return try {
            userDao.insertUser(user)
            preferencesManager.saveUsername(user.username)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun login(username: String, password: String): Boolean {
        return try {
            val user = userDao.getUser(username, password)
            if (user != null) {
                preferencesManager.saveUsername(user.username)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}
