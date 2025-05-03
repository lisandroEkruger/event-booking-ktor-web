package com.route

import com.request.NotificationRequest
import com.request.TicketRequest
import com.response.NotificationResponse
import com.service.TicketService
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*

fun Route.ticketRoutes() {
    val service = TicketService()

    route("/ticket") {

        // Formulario creación
        get("/create") {
            call.respond(ThymeleafContent("ticket/create", emptyMap<String, Any>()))
        }

        // Guardar creación
        post("/create/save") {
            val params = call.receiveParameters()
            val eventId       = params["eventId"]?.toIntOrNull() ?: throw BadRequestException("eventId inválido")
            val participantId = params["participantId"]?.toIntOrNull() ?: throw BadRequestException("participantId inválido")
            val seatNumber    = params["seatNumber"]?.toIntOrNull() ?: throw BadRequestException("seatNumber obligatorio")
            val status        = params["status"]?.toBooleanStrictOrNull() ?: throw BadRequestException("status inválido")

            service.createTicket(TicketRequest(eventId, participantId, seatNumber, status))
            call.respondRedirect("/ticket/list")
        }

        // Formulario edición
        get("/edit/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw BadRequestException("ID inválido")
            val existing = service.listAll().find { it.event_id == id }
                ?: throw NotFoundException("Ticket no encontrado")
            call.respond(
                ThymeleafContent("ticket/create", mapOf("ticket" to existing))
            )
        }

        // Actualizar
        post("/update/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw BadRequestException("ID inválido")
            val params = call.receiveParameters()
            val eventId       = params["eventId"]?.toIntOrNull() ?: throw BadRequestException("eventId inválido")
            val participantId = params["participantId"]?.toIntOrNull() ?: throw BadRequestException("participantId inválido")
            val seatNumber    = params["seatNumber"]?.toIntOrNull() ?: throw BadRequestException("seatNumber obligatorio")
            val status        = params["status"]?.toBooleanStrictOrNull() ?: throw BadRequestException("status inválido")

            service.updateTicket(id, TicketRequest(eventId, participantId, seatNumber, status))
            call.respondRedirect("/ticket/list")
        }

        // Eliminar
        post("/delete/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw BadRequestException("ID inválido")
            service.deleteTicket(id)
            call.respondRedirect("/ticket/list")
        }
    }
}
