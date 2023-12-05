package com.example.requestBody.auth

import kotlinx.serialization.Serializable

@Serializable

data class CheckAccountAvailibilityRequestBody(
    val email: String? = null,
    val username: String? = null,
    val phoneNumber: String? = null
): RequestBody
