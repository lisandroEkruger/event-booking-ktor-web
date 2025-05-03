package com.service

import com.model.Participant
import com.request.ParticipantRequest
import com.response.ParticipantResponse
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ParticipantService {

    suspend fun listAll(): List<ParticipantResponse> = dbQuery {
        Participant.selectAll().map {
            ParticipantResponse(
                id_participant = it[Participant.id_participant],
                firstName = it[Participant.firstName],
                lastName = it[Participant.lastName],
                email = it[Participant.email]
            )
        }
    }

    suspend fun createParticipant(participantRequest: ParticipantRequest) {
        transaction {
            Participant.insert {
                it[firstName] = participantRequest.firsName
                it[lastName] = participantRequest.lastName
                it[email] = participantRequest.email
            }[Participant.id_participant]
        }
    }

    suspend fun updateParticipant(id: Int, participantRequest: ParticipantRequest) {
        dbQuery {
            Participant.update({ Participant.id_participant eq id }) {
                it[firstName] = participantRequest.firsName
                it[lastName] = participantRequest.lastName
                it[email] = participantRequest.email
            }
        }
    }

    suspend fun deleteParticipant(id: Int) {
        dbQuery {
            Participant.deleteWhere { Participant.id_participant eq id }
        }
    }

    suspend fun findById(id: Int): ParticipantResponse? = dbQuery {
        Participant
            .selectAll()
            .where { Participant.id_participant eq id }
            .mapNotNull { row ->
                // DEBUG: imprime todas las columnas disponibles
                println("ROW -> $row")
                ParticipantResponse(
                    id_participant = row[Participant.id_participant],
                    firstName      = row[Participant.firstName],
                    lastName       = row[Participant.lastName],
                    email          = row[Participant.email]
                )
            }
            .singleOrNull()
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}