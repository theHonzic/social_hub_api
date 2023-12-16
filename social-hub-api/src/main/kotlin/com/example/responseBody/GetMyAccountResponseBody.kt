package com.example.responseBody

import kotlinx.serialization.Serializable

@Serializable
data class GetMyAccountResponseBody(
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: Int,
    val phoneNumber: String,
    val country: String,
    val profilePicture: String? = null
)
