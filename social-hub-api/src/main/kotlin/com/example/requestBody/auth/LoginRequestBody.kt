package com.example.requestBody.auth

import com.example.requestBody.RequestBody
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestBody(
    val login: String,
    val password: String
): RequestBody
