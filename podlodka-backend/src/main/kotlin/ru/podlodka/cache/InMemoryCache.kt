package ru.podlodka.cache

import ru.podlodka.features.RegisterReciveRemote

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {
    val userList: MutableList<RegisterReciveRemote> = mutableListOf()
    val tokenList:  MutableList<TokenCache> = mutableListOf()
}