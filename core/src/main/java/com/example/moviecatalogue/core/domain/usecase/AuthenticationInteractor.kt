package com.example.moviecatalogue.core.domain.usecase

import com.example.moviecatalogue.core.data.AuthenticationRepositoryCallback
import com.example.moviecatalogue.core.domain.model.Session
import com.example.moviecatalogue.core.domain.repository.IAuthenticationRepository
import javax.inject.Inject

class AuthenticationInteractor @Inject constructor(val authenticationRepository: IAuthenticationRepository) :
    AuthenticationUseCase {

    override fun register(
        username: String,
        password: String,
        listener: AuthenticationRepositoryCallback
    ) {
        authenticationRepository.register(username, password, listener)
    }

    override fun login(
        username: String,
        password: String,
        listener: AuthenticationRepositoryCallback
    ) {
        authenticationRepository.login(username, password, listener)
    }

    override fun logout() {
        authenticationRepository.logout()
    }

    override fun forgotPassword(username: String) {
        authenticationRepository.forgotPassword(username)
    }

    override fun getSession(): Session {
        return authenticationRepository.getSession()
    }
}