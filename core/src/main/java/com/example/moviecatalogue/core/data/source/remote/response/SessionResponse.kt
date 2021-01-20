package com.example.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SessionResponse(
    var success: Boolean,

    @SerializedName("guest_session_id")
    var guestSessionId: String,

    @SerializedName("expires_at")
    var expiresAt: String
)