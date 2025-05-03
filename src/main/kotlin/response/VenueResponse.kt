package com.response

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class VenueResponse(@Contextual val id_venue: Int, val name: String, val address: String, val capacity: Boolean)