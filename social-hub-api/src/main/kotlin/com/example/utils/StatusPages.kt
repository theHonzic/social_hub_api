package com.example.utils

import com.example.exceptions.AuthenticationException
import com.example.exceptions.UserNotFoundException
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<AuthenticationException> { call, cause ->
            call.respond(status = io.ktor.http.HttpStatusCode.Unauthorized, message = cause.message ?: "Authentication failed!")
        }
        exception<UserNotFoundException> { call, cause ->
            call.respond(status = io.ktor.http.HttpStatusCode.NotFound, message = cause.message ?: "Not found!")
        }
    }
}