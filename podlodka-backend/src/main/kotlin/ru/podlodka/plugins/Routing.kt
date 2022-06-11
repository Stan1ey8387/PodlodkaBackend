package ru.podlodka.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.podlodka.features.RegisterReciveRemote

fun Application.configureRouting() {

    routing {
        get("/test") {
            call.respondText { "Hello podlodka" }
        }
    }
}
