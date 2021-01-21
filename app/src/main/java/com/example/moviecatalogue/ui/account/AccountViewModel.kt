package com.example.moviecatalogue.ui.account

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.core.data.AuthState
import com.example.moviecatalogue.core.data.AuthenticationRepositoryCallback
import com.example.moviecatalogue.core.domain.usecase.AuthenticationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AccountViewModel @ViewModelInject constructor(val authenticationUseCase: AuthenticationUseCase) :
    ViewModel() {

    private val _registrationSuccess = MutableStateFlow<AuthState<Unit>>(AuthState.Init())
    val registrationSuccess: StateFlow<AuthState<Unit>> get() = _registrationSuccess

    private val _loginSuccess = MutableStateFlow<AuthState<Unit>>(AuthState.Init())
    val loginSuccess: StateFlow<AuthState<Unit>> get() = _loginSuccess

    private val _forgotPassword = MutableStateFlow<AuthState<Unit>>(AuthState.Init())
    val forgotPassword: StateFlow<AuthState<Unit>> get() = _forgotPassword

    fun register(username: String, password: String) {
        _registrationSuccess.value = AuthState.Loading()
        authenticationUseCase.register(
            username,
            password,
            object : AuthenticationRepositoryCallback {
                override fun onSuccess() {
                    _registrationSuccess.value = AuthState.Success()
                }

                override fun onError(message: String) {
                    _registrationSuccess.value = AuthState.Error(message)
                }

            })
    }

    fun login(username: String, password: String) {
        _loginSuccess.value = AuthState.Loading()
        authenticationUseCase.login(
            username,
            password,
            object : AuthenticationRepositoryCallback {
                override fun onSuccess() {
                    _loginSuccess.value = AuthState.Success()
                }

                override fun onError(message: String) {
                    _loginSuccess.value = AuthState.Error(message)
                }

            })
    }

    fun logout() {
        authenticationUseCase.logout()
    }

    fun forgotPassword(username: String) {
        _forgotPassword.value = AuthState.Loading()
        authenticationUseCase.forgotPassword(username, object : AuthenticationRepositoryCallback {
            override fun onSuccess() {
                _forgotPassword.value = AuthState.Success()
            }

            override fun onError(message: String) {
                _forgotPassword.value = AuthState.Error(message)
            }

        })
    }

    fun requestSession(): String {
        val session = authenticationUseCase.getSession()
//        return session.guestSessionId
        return "Session"
    }
}