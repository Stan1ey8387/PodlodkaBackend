package ru.podlodka

@kotlinx.serialization.Serializable
data class ResponseError(
    val text: String,
    val code: Int
)