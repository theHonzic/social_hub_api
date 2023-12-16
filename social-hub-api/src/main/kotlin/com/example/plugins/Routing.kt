package com.example.plugins

import com.example.routes.authRouting
import com.example.routes.userRouting
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        authRouting()
        authenticate("auth-jwt") { userRouting() }
    }
}