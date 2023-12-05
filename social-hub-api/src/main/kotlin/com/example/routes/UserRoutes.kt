package com.example.routes

import com.example.dao.user.dao
import com.example.model.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
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
        // Get specific user
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
        }
        // Add user
        post {
            val user = call.receive<User>()
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