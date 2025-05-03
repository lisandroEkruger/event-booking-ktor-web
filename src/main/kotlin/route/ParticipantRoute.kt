package com.routing

import com.request.ParticipantRequest
import com.service.ParticipantService
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*

fun Route.participantRoutes() {
    val service = ParticipantService()

    route("/participant") {

        // Formulario creación
        get("/create") {
            call.respond(ThymeleafContent("participant/create", emptyMap<String, Any>()))
        }

        // Guardar creación
        post("/create/save") {
            val params = call.receiveParameters()
            val firstName = params["firstName"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("El nombre es obligatorio")
            val lastName = params["lastName"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("El apellido es obligatorio")
            val email = params["email"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("El email es obligatorio")

            service.createParticipant(ParticipantRequest(firstName, lastName, email))
            call.respondRedirect("/participant/list")
        }

        // Actualizar
        post("/update/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw BadRequestException("ID inválido")
            val params = call.receiveParameters()
            val firstName = params["firstName"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("El nombre es obligatorio")
            val lastName = params["lastName"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("El apellido es obligatorio")
            val email = params["email"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("El email es obligatorio")

            service.updateParticipant(id, ParticipantRequest(firstName, lastName, email))
            call.respondRedirect("/participant/list")
        }

        // Eliminar
        post("/delete/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw BadRequestException("ID inválido")
            service.deleteParticipant(id)
            call.respondRedirect("/participant/list")
        }
    }
}