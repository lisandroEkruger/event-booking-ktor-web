package com.response

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse (val id_notification: String, @Contextual val participant_id: Int, val message: String, val sentAt: String)