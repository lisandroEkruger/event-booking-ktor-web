package com.service

import com.model.Event
import com.model.Notification
import com.model.Ticket
import com.model.Venue
import com.request.TicketRequest
import com.request.VenueRequest
import com.response.TicketResponse
import com.response.VenueResponse
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class VenueService {
    suspend fun listAll(): List<VenueResponse> = dbQuery {
        Venue.selectAll().map {
            VenueResponse(
                id_venue = it[Venue.id_venue],
                name     = it[Venue.name],
                address  = it[Venue.address],
                capacity = it[Venue.capacity]
            )
        }
    }

    suspend fun createVenue(req: VenueRequest): Int = dbQuery {
        Venue.insert {
            it[name]     = req.name
            it[address]  = req.address
            it[capacity] = req.capacity
        } get Venue.id_venue
    }

    suspend fun updateVenue(id: Int, venueRequest: VenueRequest) {
        dbQuery {
            Venue.update({ Venue.id_venue eq id }) {
                it[name] = venueRequest.name
                it[address] = venueRequest.address
                it[capacity] = venueRequest.capacity
            }
        }
    }

    suspend fun deleteVenue(id: Int): Boolean = dbQuery {
        Venue.deleteWhere { Venue.id_venue eq id } > 0
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}