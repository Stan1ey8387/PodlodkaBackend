package ru.podlodka.features.register

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.podlodka.ResponseError
import ru.podlodka.cache.InMemoryCache
import ru.podlodka.cache.TokenCache
import ru.podlodka.database.tokens.TokenDTO
import ru.podlodka.database.tokens.TokenModel
import ru.podlodka.database.users.UserDTO
import ru.podlodka.database.users.UserModel
import ru.podlodka.features.LoginResponseRemote
import ru.podlodka.features.RegisterReciveRemote
import ru.podlodka.utils.isValidEmail
import java.util.*

class RegisterController(private val call: ApplicationCall) {
    suspend fun register() {
        val registerReciveRemote = call.receive<RegisterReciveRemote>()
        if (!registerReciveRemote.email.isValidEmail()) {
            call.respond(ResponseError(text = "Введен некорректный email адрес", code = 400))
        }

        val userDto = UserModel.fetchUser(registerReciveRemote.login)
        val isUserExist = userDto != null

        if (isUserExist) {
            call.respond(ResponseError(text = "Пользователь с таким логином уже существует", code = 400))
        }
        else {
            val token = UUID.randomUUID().toString()

            try {
                TokenModel.insert(TokenDTO(
                    id = UUID.randomUUID().toString(),
                    login = registerReciveRemote.login,
                    token = token
                ))
            } catch (e: ExposedSQLException) {
                call.respond(ResponseError(text = "Пользователь с таким логином уже существует", code = 400))
            }

            UserModel.insert(
                UserDTO(
                    login = registerReciveRemote.login,
                    password = registerReciveRemote.password,
                    username = "",
                    email = registerReciveRemote.email
                )
            )

            call.respond(LoginResponseRemote(token))
        }

//        if (InMemoryCache.userList.map { it.login }.contains(registerRecive.login)) {
//            call.respond(ResponseError(text = "Пользователь с таким логином уже существует", code = 400))
//        }
//        else if (!registerRecive.email.isValidEmail()) {
//            call.respond(ResponseError(text = "Введен некорректный email адрес", code = 400))
//        }
//        else {
//            val token = UUID.randomUUID().toString()
//            InMemoryCache.userList.add(registerRecive)
//            InMemoryCache.tokenList.add(TokenCache(login = registerRecive.login, token = token))
//            call.respond(LoginResponseRemote(token))
//        }
    }
}