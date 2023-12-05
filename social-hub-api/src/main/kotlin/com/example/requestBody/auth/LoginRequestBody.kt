package com.example.requestBody.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestBody(
    val login: String,
    val password: String
): RequestBody
