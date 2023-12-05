package com.example.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.exceptions.AuthenticationException
import com.example.utils.Logger
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.plugins.*
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class TokenManager {
    private val config = HoconApplicationConfig(ConfigFactory.load())

    private val audience = config.property("ktor.security.jwt.audience").getString()
    private val secret = config.property("ktor.security.jwt.secret").getString()
    private val issuer = config.property("ktor.security.jwt.issuer").getString()
    private val expirationPeriod = config.property("ktor.security.jwt.expiration_time").getString().toLong()
    private val algorithm = Algorithm.HMAC512(secret)

    fun generateToken(username: String) =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", username)
            .withExpiresAt(getExpirationTime())
            .sign(algorithm)

    private fun getExpirationTime() =
        Date(System.currentTimeMillis() + expirationPeriod)

    @OptIn(ExperimentalEncodingApi::class)
    fun parseJWT(token: String): Result<TokenPayload> {
        return try {
            val parts = token.split(".")
            val charset = charset("UTF-8")
            val payload = String(Base64.decode(parts[1].toByteArray(charset)), charset)
            Logger.log(Json.decodeFromString(payload), Logger.MessageKind.INFO)
            Result.success(Json.decodeFromString(payload.replace("{", "").replace("}", "")))
        } catch (e: Exception) {
            Logger.log(e.message.toString(), Logger.MessageKind.ERROR)
            Result.failure(e)
        }
    }
}

fun Application.configureSecurity() {
    install(Authentication) {
        jwt {
            val config = HoconApplicationConfig(ConfigFactory.load())
            realm = config.property("ktor.security.jwt.realm").getString()

            val secret = config.property("ktor.security.jwt.secret").getString()
            val issuer = config.property("ktor.security.jwt.issuer").getString()
            val audience = config.property("ktor.security.jwt.audience").getString()

            challenge { _, _ ->
                call.request.headers["Authorization"]?.let {
                    if (it.isNotEmpty()) {
                        throw AuthenticationException("Token Expired")
                    } else {
                        throw BadRequestException("Authorization header can not be blank!")
                    }
                } ?: throw BadRequestException("Authorization header can not be blank!")
            }

            verifier(
                JWT.require(Algorithm.HMAC512(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )
            validate { credential ->
                if (!credential.payload.getClaim("username").asString().isNullOrEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}