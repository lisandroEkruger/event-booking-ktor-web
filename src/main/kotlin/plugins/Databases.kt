package com.plugins

import com.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

fun seedDatabase() {
    transaction {
        // Insertar participantes
        Participant.insert {
            it[firstName] = "Lisandro"
            it[lastName] = "Kruger"
            it[email] = "lisandro@kruger.com"
        }
        Participant.insert {
            it[firstName] = "María"
            it[lastName] = "González"
            it[email] = "maria.gonzalez@example.com"
        }
        Participant.insert {
            it[firstName] = "Javier"
            it[lastName] = "Pérez"
            it[email] = "javier.perez@example.com"
        }

        // Insertar lugares (venues)
        Venue.insert {
            it[name] = "Auditorio Central"
            it[address] = "Av. Siempre Viva 123"
            it[capacity] = true
        }
        Venue.insert {
            it[name] = "Sala de Conciertos"
            it[address] = "Calle Falsa 456"
            it[capacity] = true
        }
        Venue.insert {
            it[name] = "Pequeño Teatro"
            it[address] = "Pasaje Corto 789"
            it[capacity] = false
        }

        // Insertar eventos
        Event.insert {
            it[name] = "Concierto Rock 2025"
            it[date] = LocalDate.parse("2025-06-15")
        }
        Event.insert {
            it[name] = "Taller de Pintura"
            it[date] = LocalDate.parse("2025-07-02")
        }
        Event.insert {
            it[name] = "Feria de Libros"
            it[date] = LocalDate.parse("2025-08-20")
        }

        // Insertar entradas (tickets)
        Ticket.insert {
            it[id_ticket] = 1
            it[event_id] = 1
            it[participant_id] = 1
            it[seatNumber] = 12
            it[status] = true
        }
        Ticket.insert {
            it[id_ticket] = 2
            it[event_id] = 1
            it[participant_id] = 2
            it[seatNumber] = 15
            it[status] = true
        }
        Ticket.insert {
            it[id_ticket] = 3
            it[event_id] = 2
            it[participant_id] = 3
            it[seatNumber] = 5
            it[status] = false
        }
        Ticket.insert {
            it[id_ticket] = 4
            it[event_id] = 3
            it[participant_id] = 1
            it[seatNumber] = 101
            it[status] = true
        }

        // Insertar notificaciones
        Notification.insert {
            it[participant_id] = 1
            it[message] = "Tu entrada está confirmada"
            it[sentAt] = "2025-05-03T10:00"
        }
        Notification.insert {
            it[participant_id] = 2
            it[message] = "Recordatorio: Taller mañana"
            it[sentAt] = "2025-06-14T18:30"
        }
        Notification.insert {
            it[participant_id] = 3
            it[message] = "Feria de libros: ¡últimos días!"
            it[sentAt] = "2025-08-19T09:15"
        }
    }
}

object DatabaseFactory {
    fun init() {
        // Conecta a H2 en memoria
        Database.connect(
            url    = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
            driver = "org.h2.Driver",
            user   = "root",
            password = ""
        )

        transaction {
            SchemaUtils.create(
                Event,
                Notification,
                Participant,
                Ticket,
                Venue
            )
            seedDatabase()
        }
    }
}
