package com.example.dao.user

import com.example.model.User

interface IDaoFacade {
    // User query
    suspend fun getUser(username: String): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun getUserByUserName(userName: String): User?
    suspend fun getUserByPhoneNumber(phoneNumber: String): User?
    suspend fun getAllUsers(): List<User>
    suspend fun addUser(
        username: String,
        firstName: String,
        lastName: String,
        gender: String,
        email: String,
        phoneNumber: String,
        country: String,
        password: String
    ): User?
    suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(username: String): Boolean
    suspend fun checkCredentials(username: String, password: String): Boolean
}