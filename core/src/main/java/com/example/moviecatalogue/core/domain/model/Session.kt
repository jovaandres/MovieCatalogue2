package com.example.moviecatalogue.core.domain.model

data class Session(
    var success: Boolean,
    var guestSessionId: String?,
    var expiresAt: String?
)