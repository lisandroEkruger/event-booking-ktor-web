package com.route

import com.request.VenueRequest
import com.service.VenueService
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*

fun Route.venueRoutes() {
    val service = VenueService()

    route("/venue") {

        // Formulario creación
        get("/create") {
            call.respond(ThymeleafContent("venue/create", emptyMap<String, Any>()))
        }

        // Guardar creación
        post("/create/save") {
            val params = call.receiveParameters()
            val name = params["name"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("El nombre es obligatorio")
            val address = params["address"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("La dirección es obligatoria")
            val capacity = params["capacity"]?.toBoolean()
                ?: throw BadRequestException("La capacidad debe ser un número")

            service.createVenue(VenueRequest(name, address, capacity))
            call.respondRedirect("/venue/list")
        }

        // Formulario edición
        get("/edit/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw BadRequestException("ID inválido")
            val existing = service.listAll().find { it.id_venue == id }
                ?: throw NotFoundException("Sede no encontrada")
            call.respond(
                ThymeleafContent("venue/create", mapOf("venue" to existing))
            )
        }

        // Actualizar
        post("/update/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw BadRequestException("ID inválido")
            val params = call.receiveParameters()
            val name = params["name"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("El nombre es obligatorio")
            val address = params["address"].orEmpty().takeIf { it.isNotBlank() }
                ?: throw BadRequestException("La dirección es obligatoria")
            val capacity = params["capacity"]?.toBoolean()
                ?: throw BadRequestException("La capacidad debe ser un número")

            service.updateVenue(id, VenueRequest(name, address, capacity))
            call.respondRedirect("/venue/list")
        }

        // Eliminar
        post("/delete/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw BadRequestException("ID inválido")
            service.deleteVenue(id)
            call.respondRedirect("/venue/list")
        }
    }
}