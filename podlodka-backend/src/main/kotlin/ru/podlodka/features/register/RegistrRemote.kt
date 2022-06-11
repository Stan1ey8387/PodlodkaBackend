package ru.podlodka.features

@kotlinx.serialization.Serializable
data class RegisterReciveRemote(
    val login: String,
    val email: String,
    val password: String
)


@kotlinx.serialization.Serializable
data class RegisterResponseRemote(
    val token: String
)