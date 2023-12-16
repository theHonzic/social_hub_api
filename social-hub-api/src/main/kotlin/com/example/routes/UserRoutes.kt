package com.example.routes

import com.example.dao.user.dao
import com.example.model.User
import com.example.responseBody.GetMyAccountResponseBody
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRouting() {
    route("/user") {
        // Get all users
        get {
            val users = dao.getAllUsers()
            if (users.isEmpty()) {
                call.respondText("No users found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(users)
            }
        }
        // Get account data
        get("/account") {
            val principal = call.principal<JWTPrincipal>()
            principal?.payload?.getClaim("username")?.asString()?.let {
                dao.getUserByUserName(it)?.let { user ->
                    call.respond(GetMyAccountResponseBody(
                        user.username,
                        user.email,
                        user.firstName,
                        user.lastName,
                        user.gender,
                        user.phoneNumber,
                        user.country,
                        "https://randomuser.me/api/portraits/lego/6.jpg"
                    ))
                } ?: call.respondText("User not found", status = HttpStatusCode.NotFound)
            } ?: call.respondText("Invalid token", status = HttpStatusCode.Unauthorized)
        }
        // Get specific user
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
        }
        /*
        // Delete
        delete("{username?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            dao.deleteUser(id.toInt()).let {
                if (it) call.respondText("User deleted", status = HttpStatusCode.OK)
                else call.respondText("User not found", status = HttpStatusCode.NotFound)
            }
        }
         */
    }
}