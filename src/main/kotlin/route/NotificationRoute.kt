package com.routing

import com.request.NotificationRequest
import com.service.NotificationService
import com.service.ParticipantService
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*

fun Route.notificationRoutes() {
    val notificationService = NotificationService()
    val participantService = ParticipantService()

    route("/notification") {

        // Formulario creación
        get("/create") {
            val participants = participantService.listAll()
            call.respond(
                ThymeleafContent("notification/create", mapOf("participants" to participants))
            )
        }

        // Guardar nuevo
        post("/create/save") {
            val params = call.receiveParameters()
            val participantId = params["participant_id"]?.toIntOrNull()
                ?: throw BadRequestException("participantId inválido")
            val participant = participantService.findById(participantId)
                ?: throw IllegalArgumentException("Participante no encontrado")
            val message = params["message"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("El mensaje es obligatorio")
            val sentAt = params["sentAt"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("La fecha es obligatoria")

            notificationService.createNotification(NotificationRequest(participant.id_participant, message, sentAt))
            call.respondRedirect("/notification/list")
        }

        // Actualizar
        post("/update/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("ID inválido")
            val params = call.receiveParameters()
            val participantId = params["participantId"]?.toIntOrNull()
                ?: throw BadRequestException("participantId inválido")
            val message = params["message"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("El mensaje es obligatorio")
            val sentAt = params["sentAt"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("La fecha es obligatoria")

            notificationService.updateNotification(id, NotificationRequest(participantId, message, sentAt))
            call.respondRedirect("/notification/list")
        }

        // Eliminar
        post("/delete/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("ID inválido")
            notificationService.deleteNotification(id)
            call.respondRedirect("/notification/list")
        }
    }
}