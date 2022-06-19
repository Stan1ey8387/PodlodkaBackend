package ru.podlodka.features.login

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.podlodka.ResponseError
import ru.podlodka.cache.InMemoryCache
import ru.podlodka.cache.TokenCache
import ru.podlodka.features.LoginReciveRemote
import ru.podlodka.features.LoginResponseRemote
import java.util.UUID

fun Application.configureLoginRouting() {

    routing {
        post("/login") {
            val loginController = LoginController(call)
            loginController.login()
        }
    }
}