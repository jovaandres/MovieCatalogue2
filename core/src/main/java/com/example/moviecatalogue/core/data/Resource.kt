package com.example.moviecatalogue.core.data

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Init<T>(data: T? = null) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

sealed class AuthState<T>(val message: String? = null) {
    class Init<T> : AuthState<T>()
    class Loading<T> : AuthState<T>()
    class Success<T> : AuthState<T>()
    class Error<T>(message: String) : AuthState<T>(message)
}