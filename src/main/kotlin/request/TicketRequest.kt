package com.request

import com.model.Event
import com.model.Participant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class TicketRequest(@Contextual val event_id: Int, @Contextual val participant_id: Int, val seatNumber: Int, val status: Boolean)