package com.example.moviecatalogue.core.data

import android.content.Context
import com.example.moviecatalogue.core.domain.repository.IAuthenticationRepository
import com.example.moviecatalogue.core.utils.UserPreferences
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    val auth: FirebaseAuth,
    @ApplicationContext val context: Context
) :
    IAuthenticationRepository {

    override fun register(username: String, password: String) {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val prefs = UserPreferences(context)
                    prefs.username = username
                    prefs.password = password
                }
            }
    }

    override fun login(username: String, password: String) {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val prefs = UserPreferences(context)
                    prefs.username = username
                    prefs.password = password
                }
            }
    }
}