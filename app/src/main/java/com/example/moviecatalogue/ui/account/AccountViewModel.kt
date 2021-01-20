package com.example.moviecatalogue.ui.account

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.core.data.AuthenticationRepositoryCallback
import com.example.moviecatalogue.core.domain.usecase.AuthenticationUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class AccountViewModel @ViewModelInject constructor(val authenticationUseCase: AuthenticationUseCase) :
    ViewModel() {

    val email = MutableStateFlow("Guest")
    val registrationSuccess = MutableStateFlow(false)
    val loginSuccess = MutableStateFlow(false)
    val session = MutableStateFlow("")

    fun register(username: String, password: String) {
        authenticationUseCase.register(
            username,
            password,
            object : AuthenticationRepositoryCallback {
                override fun onSuccess(username: String) {
                    registrationSuccess.value = true
                }

                override fun onError(message: String) {
                    registrationSuccess.value = false
                }

            })
    }

    fun login(username: String, password: String) {
        authenticationUseCase.login(
            username,
            password,
            object : AuthenticationRepositoryCallback {
                override fun onSuccess(username: String) {
                    loginSuccess.value = true
                }

                override fun onError(message: String) {
                    loginSuccess.value = false
                }

            })
    }

    fun logout() {
        authenticationUseCase.logout()
    }

    fun forgotPassword(username: String) {
        authenticationUseCase.forgotPassword(username)
    }

    fun requestSession() {
        val session = authenticationUseCase.getSession()
        this.session.value = session.guestSessionId.toString()
    }
}