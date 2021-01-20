package com.example.moviecatalogue.core.domain.usecase

import com.example.moviecatalogue.core.domain.repository.IAuthenticationRepository
import javax.inject.Inject

class AuthenticationInteractor @Inject constructor(val authenticationRepository: IAuthenticationRepository) :
    AuthenticationUseCase {

    override fun register(username: String, password: String) {
        authenticationRepository.register(username, password)
    }

    override fun login(username: String, password: String) {
        authenticationRepository.login(username, password)
    }
}