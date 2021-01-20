package com.example.moviecatalogue.core.domain.usecase

import com.example.moviecatalogue.core.data.AuthenticationRepositoryCallback
import com.example.moviecatalogue.core.domain.model.Session

interface AuthenticationUseCase {

    fun register(username: String, password: String, listener: AuthenticationRepositoryCallback)

    fun login(username: String, password: String, listener: AuthenticationRepositoryCallback)

    fun logout()

    fun forgotPassword(username: String)

    fun getSession(): Session

}