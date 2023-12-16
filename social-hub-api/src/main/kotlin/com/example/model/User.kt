package com.example.model

import kotlinx.serialization.Serializable

// API model
@Serializable
data class User(
    val username: String,
    val firstName: String,
    val lastName: String,
    val gender: Int,
    val email: String,
    val phoneNumber: String,
    val country: String
)