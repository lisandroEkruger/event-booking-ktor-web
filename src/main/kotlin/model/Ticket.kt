package com.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Ticket: Table() {
    val id_ticket: Column<Int> = integer("id_ticket")
    val event_id: Column<Int> = reference("event_id", Event.id_event)
    val participant_id: Column<Int> = reference("participant_id", Participant.id_participant)
    val seatNumber: Column<Int> = integer("seat_number")
    val status: Column<Boolean> = bool("status")
    override val primaryKey = PrimaryKey(id_ticket)
}