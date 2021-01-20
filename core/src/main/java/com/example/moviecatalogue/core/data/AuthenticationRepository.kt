package com.example.moviecatalogue.core.data

import android.content.Context
import com.example.moviecatalogue.core.data.source.remote.api.TheMovieDBService
import com.example.moviecatalogue.core.data.source.remote.response.SessionResponse
import com.example.moviecatalogue.core.domain.model.Session
import com.example.moviecatalogue.core.domain.repository.IAuthenticationRepository
import com.example.moviecatalogue.core.utils.Constant.API_KEY
import com.example.moviecatalogue.core.utils.UserPreferences
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    val auth: FirebaseAuth,
    val theMovieDBService: TheMovieDBService,
    val prefs: UserPreferences,
    @ApplicationContext val context: Context
) :
    IAuthenticationRepository {

    override fun register(
        username: String,
        password: String,
        listener: AuthenticationRepositoryCallback
    ) {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    listener.onSuccess(username)
                    prefs.username = username
                    prefs.password = password
                } else {
                    listener.onError(it.exception?.message.toString())
                }
            }
    }

    override fun login(
        username: String,
        password: String,
        listener: AuthenticationRepositoryCallback
    ) {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    listener.onSuccess(username)
                    prefs.username = username
                    prefs.password = password
                } else {
                    listener.onError(it.exception?.message.toString())
                }
            }
    }

    override fun logout() {
        auth.signOut()
        prefs.username = "Guest"
    }

    override fun forgotPassword(username: String) {
        auth.sendPasswordResetEmail(username)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                } else {

                }
            }
    }

    override fun getSession(): Session {
        val session = Session(false, null, null)
        theMovieDBService.getSession(API_KEY)
            .enqueue(object : Callback<SessionResponse> {
                override fun onResponse(
                    call: Call<SessionResponse>,
                    response: Response<SessionResponse>
                ) {
                    val responses = response.body()
                    session.success = responses?.success == true
                    session.guestSessionId = responses?.guestSessionId
                    session.expiresAt = responses?.expiresAt
                }

                override fun onFailure(call: Call<SessionResponse>, t: Throwable) {

                }

            })
        return session
    }
}