package com.example.moviecatalogue.core.domain.usecase

interface AuthenticationUseCase {

    fun register(username: String, password: String)

    fun login(username: String, password: String)

}