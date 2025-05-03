package com.request

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantRequest(val firsName: String, val lastName: String, val email: String)
