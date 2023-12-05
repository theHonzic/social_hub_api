package com.example.requestBody.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequestBody(
    val username: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val email: String,
    val phoneNumber: String,
    val country: String,
    val password: String
): RequestBody