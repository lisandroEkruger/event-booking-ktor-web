package com.request

import kotlinx.serialization.Serializable

@Serializable
data class VenueRequest(val name: String, val address: String, val capacity: Boolean)
