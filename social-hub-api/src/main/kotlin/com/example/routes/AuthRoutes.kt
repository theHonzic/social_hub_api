package com.example.routes

import com.example.dao.user.dao
import com.example.exceptions.UserNotFoundException
import com.example.model.User
import com.example.requestBody.auth.CheckAccountAvailabilityRequestBody
import com.example.requestBody.auth.LoginRequestBody
import com.example.requestBody.auth.RegistrationRequestBody
import com.example.security.TokenManager
import com.example.utils.Logger
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.h2.security.auth.AuthenticationException

fun Route.authRouting() {
    val tokenManager = TokenManager()

    route("/auth") {
        // Login
        post("/login") {
            val requestBody = call.receive<LoginRequestBody>()
            val user: User? = if (requestBody.login.contains("@"))
                dao.getUserByEmail(requestBody.login)
            else
                dao.getUserByUserName(requestBody.login)
            user?.let {
                if (dao.checkCredentials(it.username, requestBody.password)) {
                    call.respond(hashMapOf("token" to tokenManager.generateToken(it.username)))
                } else {
                    throw AuthenticationException("Invalid username or password!")
                }
            } ?: throw UserNotFoundException()
        }

        // Register
        post("/register") {
            val body = call.receive<RegistrationRequestBody>()

            val used = listOfNotNull(
                body.username.let { it1 -> dao.getUser(it1) },
                body.email.let { it1 -> dao.getUserByEmail(it1) },
                body.phoneNumber.let { it1 -> dao.getUserByPhoneNumber(it1) }
            ).isNotEmpty()

            if (used) {
                Logger.log("Account already exists", Logger.MessageKind.ERROR)
                call.respondText("Account already exists", status = HttpStatusCode.Conflict)
            } else {
                dao.addUser(
                    body.username,
                    body.firstName,
                    body.lastName,
                    body.gender,
                    body.email,
                    body.phoneNumber,
                    body.country,
                    body.password
                )?.let {
                    // User created
                    call.respond(hashMapOf("token" to tokenManager.generateToken(it.username)))
                } ?: throw AuthenticationException("User could not be created!")
            }
        }

        // Check account is available
        post("/check_available") {
            val body = call.receive<CheckAccountAvailabilityRequestBody>()

            val used = listOfNotNull(
                body.username?.let { it1 -> dao.getUser(it1) },
                body.email?.let { it1 -> dao.getUserByEmail(it1) },
                body.phoneNumber?.let { it1 -> dao.getUserByPhoneNumber(it1) }
            ).isNotEmpty()
            if (used) {
                call.respondText("Account is not available", status = HttpStatusCode.Conflict)
            } else {
                call.respondText("Account is available", status = HttpStatusCode.OK)
            }
        }
    }
}