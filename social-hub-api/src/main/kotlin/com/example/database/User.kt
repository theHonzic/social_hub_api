package com.example.database

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {
    val username = varchar("username", 50)
    val firstName = varchar("firstName", 50)
    val lastName = varchar("lastName", 50)
    val gender = integer("gender")
    val email = varchar("email", 50)
    val phoneNumber = varchar("phoneNumber", 20)
    val country = varchar("country", 2)
    val password = varchar("password", 100)
    override val primaryKey = PrimaryKey(username)
}