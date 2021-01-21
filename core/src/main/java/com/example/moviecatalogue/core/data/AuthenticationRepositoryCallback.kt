package com.example.moviecatalogue.core.data

interface AuthenticationRepositoryCallback {

    fun onSuccess()

    fun onError(message: String)
}