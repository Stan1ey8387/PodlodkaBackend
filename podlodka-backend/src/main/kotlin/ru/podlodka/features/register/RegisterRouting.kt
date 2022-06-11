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
            val registerRecive = call.receive<RegisterReciveRemote>()

            if (InMemoryCache.userList.map { it.login }.contains(registerRecive.login)) {
                call.respond(ResponseError(text = "Пользователь с таким логином уже существует", code = 400))
            }
            else if (!registerRecive.email.isValidEmail()) {
                call.respond(ResponseError(text = "Введен некорректный email адрес", code = 400))
            }
            else {
                val token = UUID.randomUUID().toString()
                InMemoryCache.userList.add(registerRecive)
                InMemoryCache.tokenList.add(TokenCache(login = registerRecive.login, token = token))
                call.respond(LoginResponseRemote(token))
            }
        }
    }
}