package ru.podlodka.features.register

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.podlodka.ResponseError
import ru.podlodka.cache.InMemoryCache
import ru.podlodka.cache.TokenCache
import ru.podlodka.features.LoginResponseRemote
import ru.podlodka.features.RegisterReciveRemote
import ru.podlodka.utils.isValidEmail
import java.util.UUID

fun Application.configureRegisterRouting() {

    routing {
        post("/register") {
            val registerController = RegisterController(call)
            registerController.register()
        }
    }
}