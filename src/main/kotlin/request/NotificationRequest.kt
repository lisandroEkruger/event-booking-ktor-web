package com.request

import com.model.Participant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class NotificationRequest (@Contextual val participant_id: Int, val message: String, val sentAt: String)
