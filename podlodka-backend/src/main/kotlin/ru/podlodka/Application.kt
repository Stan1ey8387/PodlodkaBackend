package ru.podlodka

import io.ktor.server.engine.*
import io.ktor.server.cio.*
import org.jetbrains.exposed.sql.Database
import ru.podlodka.features.login.configureLoginRouting
import ru.podlodka.features.register.configureRegisterRouting
import ru.podlodka.plugins.*

fun main() {
    connectDatabase()
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()

        configureLoginRouting()
        configureRegisterRouting()
    }.start(wait = true)
}

private fun connectDatabase() {
    val databaseName: String = "podlodka"
    val databasePassword: String = "8387"

    Database.connect("jdbc:postgresql://localhost:5432/$databaseName", driver = "org.postgresql.Driver", password = databasePassword, user = "postgres")
}