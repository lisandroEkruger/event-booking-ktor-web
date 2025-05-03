package com.response

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class EventResponse(val id_event: Int, val name: String, @Contextual val dateEvent: LocalDate)