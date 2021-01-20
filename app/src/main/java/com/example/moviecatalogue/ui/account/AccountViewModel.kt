package com.example.moviecatalogue.ui.account

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.core.domain.usecase.AuthenticationUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class AccountViewModel @ViewModelInject constructor(val authenticationUseCase: AuthenticationUseCase) :
    ViewModel() {

    val username = MutableStateFlow("")
    val password = MutableStateFlow("")

    fun register(username: String, password: String) {
        authenticationUseCase.register(username, password)
        this.username.value = username
        this.password.value = password
    }

    fun login(username: String, password: String) {
        authenticationUseCase.login(username, password)
        this.username.value = username
        this.password.value = password
    }
}