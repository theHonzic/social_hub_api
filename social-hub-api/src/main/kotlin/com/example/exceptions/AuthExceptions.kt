package com.example.exceptions

data class AuthenticationException(
    override val message: String? = null,
    override val cause: Throwable? = null
): Throwable(message, cause)
data class UserNotFoundException(
    override val message: String? = null,
    override val cause: Throwable? = null
): Throwable(message, cause)