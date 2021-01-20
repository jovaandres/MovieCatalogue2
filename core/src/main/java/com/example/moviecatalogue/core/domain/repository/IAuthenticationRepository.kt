package com.example.moviecatalogue.core.domain.repository

interface IAuthenticationRepository {

    fun register(username: String, password: String)

    fun login(username: String, password: String)
}