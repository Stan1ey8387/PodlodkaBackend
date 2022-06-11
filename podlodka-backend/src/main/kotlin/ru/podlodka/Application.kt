package ru.podlodka

import io.ktor.server.engine.*
import io.ktor.server.cio.*
import ru.podlodka.features.login.configureLoginRouting
import ru.podlodka.features.register.configureRegisterRouting
import ru.podlodka.plugins.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()

        configureLoginRouting()
        configureRegisterRouting()
    }.start(wait = true)
}
