package com.response

import com.model.Event
import com.model.Participant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class TicketResponse(@Contextual val event_id: Int, @Contextual val participant_id: Int, val seatNumber: Int, val status: String)