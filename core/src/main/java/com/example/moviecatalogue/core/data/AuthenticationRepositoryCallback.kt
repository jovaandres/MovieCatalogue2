package com.example.moviecatalogue.core.data

interface AuthenticationRepositoryCallback {

    fun onSuccess(username: String)

    fun onError(message: String)
}