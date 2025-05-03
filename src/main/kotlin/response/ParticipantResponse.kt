package com.response

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantResponse(@Contextual val id_participant: Int, val firstName: String, val lastName: String, val email: String)