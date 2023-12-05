package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

// API model
@Serializable
data class User(
    val username: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val email: String,
    val phoneNumber: String,
    val country: String
)

// Database model
object UserTable: Table() {
    val username = varchar("username", 50)
    val firstName = varchar("firstName", 50)
    val lastName = varchar("lastName", 50)
    val gender = varchar("gender", 10)
    val email = varchar("email", 50)
    val phoneNumber = varchar("phoneNumber", 20)
    val country = varchar("country", 50)
    val password = varchar("password", 100)
    override val primaryKey = PrimaryKey(username)
}