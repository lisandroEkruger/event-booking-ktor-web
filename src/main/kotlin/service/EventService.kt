package com.service


import com.model.Event
import com.request.EventRequest
import com.response.EventResponse
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class EventService() {

    suspend fun listAll(): List<EventResponse> = dbQuery {
        Event.selectAll().map {
            EventResponse(
                id_event = it[Event.id_event],
                name = it[Event.name],
                dateEvent = it[Event.date]
            )
        }
    }

    suspend fun createEvent(eventRequest: EventRequest) {
        transaction {
            Event.insert {
                it[Event.name] = eventRequest.name
                it[Event.date] = LocalDate.now()
            }[Event.id_event]
        }
    }

    suspend fun updateEvent(id: Int, eventRequest: EventRequest) {
        dbQuery {
            Event.update({ Event.id_event eq id }) {
                it[name] = eventRequest.name
            }
        }
    }

    suspend fun deleteEvent(id: Int) {
        dbQuery {
            Event.deleteWhere { Event.id_event eq id }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}

