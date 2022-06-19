package ru.podlodka.features.login

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.podlodka.ResponseError
import ru.podlodka.cache.InMemoryCache
import ru.podlodka.cache.TokenCache
import ru.podlodka.database.tokens.TokenDTO
import ru.podlodka.database.tokens.TokenModel
import ru.podlodka.database.users.UserDTO
import ru.podlodka.database.users.UserModel
import ru.podlodka.features.LoginReciveRemote
import ru.podlodka.features.LoginResponseRemote
import ru.podlodka.features.RegisterReciveRemote
import ru.podlodka.utils.isValidEmail
import java.util.*

class LoginController(private val call: ApplicationCall) {
    suspend fun login() {
        val loginReciveRemote = call.receive(LoginReciveRemote::class)
        val userDto = UserModel.fetchUser(loginReciveRemote.login)
        val isUserExist = userDto != null

        if (userDto != null && userDto.password == loginReciveRemote.password) {
            val token = UUID.randomUUID().toString()
            TokenModel.insert(TokenDTO(
                id = UUID.randomUUID().toString(),
                login = loginReciveRemote.login,
                token = token
            ))
            call.respond(LoginResponseRemote(token))
        }

        call.respond(ResponseError(text = "Введен неверный логин или пароль", code = 123))
    }
}