package ru.podlodka.features

@kotlinx.serialization.Serializable
data class LoginReciveRemote(
    val login: String,
    val password: String
)


@kotlinx.serialization.Serializable
data class LoginResponseRemote(
    val token: String
)