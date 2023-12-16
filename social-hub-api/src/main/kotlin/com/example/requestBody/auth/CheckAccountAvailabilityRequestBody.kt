package com.example.requestBody.auth

import com.example.requestBody.RequestBody
import kotlinx.serialization.Serializable

@Serializable

data class CheckAccountAvailabilityRequestBody(
    val email: String? = null,
    val username: String? = null,
    val phoneNumber: String? = null
): RequestBody
