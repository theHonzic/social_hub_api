package com.example.security

import kotlinx.serialization.Serializable

@Serializable
data class TokenPayload(
    val aud: String,
    val iss: String,
    val username: String,
    val exp: Long
)