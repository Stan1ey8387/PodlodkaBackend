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
            val loginRecive = call.receive(LoginReciveRemote::class)
            var user = InMemoryCache.userList.firstOrNull() { it.login == loginRecive.login }

            if (user != null && user.password == loginRecive.password) {
                val token = UUID.randomUUID().toString()
                InMemoryCache.tokenList.add(TokenCache(login = loginRecive.login, token = token))
                call.respond(LoginResponseRemote(token))
            }

            call.respond(ResponseError(text = "Введен неверный логин или пароль", code = 400))
        }
    }
}