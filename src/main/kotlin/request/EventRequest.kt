package com.request

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class EventRequest(val name: String, @Contextual val dateEvent: LocalDate)