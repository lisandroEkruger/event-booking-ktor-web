package com.service

import com.model.Ticket
import com.request.TicketRequest
import com.response.TicketResponse
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TicketService {

    suspend fun listAll(): List<TicketResponse> = dbQuery {
        Ticket.selectAll().map {
            TicketResponse(
                event_id = it[Ticket.event_id],
                participant_id = it[Ticket.event_id],
                seatNumber = it[Ticket.seatNumber],
                status = it[Ticket.status].toString()

            )
        }
    }

    suspend fun createTicket(ticketRequest: TicketRequest) {
        transaction {
            Ticket.insert {
                it[event_id] = ticketRequest.event_id
                it[participant_id] = ticketRequest.participant_id
                it[seatNumber] = ticketRequest.seatNumber
                it[status] = ticketRequest.status
            }[Ticket.id_ticket]
        }
    }

    suspend fun updateTicket(id: Int, ticketRequest: TicketRequest) {
        dbQuery {
            Ticket.update({Ticket.id_ticket eq id }) {
                it[participant_id] = ticketRequest.participant_id
                it[seatNumber] = ticketRequest.seatNumber
                it[status] = ticketRequest.status
            }
        }
    }

    suspend fun deleteTicket(id: Int) {
        dbQuery {
            Ticket.deleteWhere { Ticket.id_ticket eq id }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}