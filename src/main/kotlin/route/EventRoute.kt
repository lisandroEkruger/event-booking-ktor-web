package com.routing

import com.request.EventRequest
import com.response.*
import com.service.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import java.time.LocalDate

fun Route.eventRoutes() {
    val eventService = EventService()
    val notificationService = NotificationService()
    val participantService = ParticipantService()
    val ticketService = TicketService()
    val venueService = VenueService()

    route("/event") {

        get("/list") {
            // Traigo todas las listas de tu servicio
            val events: List<EventResponse> = eventService.listAll()
            val notifications: List<NotificationResponse> = notificationService.listAll()
            val participants: List<ParticipantResponse> = participantService.listAll()
            val tickets: List<TicketResponse> = ticketService.listAll()
            val venues: List<VenueResponse> = venueService.listAll()

            // Combino todas las colecciones en un solo Map
            val model: Map<String, Any> = mapOf(
                "events"    to events,
                "notifications" to notifications,
                "participants"  to participants,
                "tickets"   to tickets,
                "venues"    to venues
            )

            // Paso el modelo completo a ThymeleafContent
            call.respond(
                ThymeleafContent("event/index", model)
            )
        }

        get("/create") {
            call.respond(
                ThymeleafContent("event/create", emptyMap<String, Any>())
            )
        }

        post("/create/save") {
            val params = call.receiveParameters()

            val name = params["nameEvent"] ?: throw IllegalArgumentException("El nombre es obligatorio")
            val dateEvent = params["dateEvent"] ?: throw IllegalArgumentException("La fecha es obligatorio")

            val eventRequest = EventRequest(name, LocalDate.parse(dateEvent))

            eventService.createEvent(eventRequest)
            call.respondRedirect("/event/list") // <-- nota la barra "/" al principio
        }

        post("/update/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("ID inválido")
            val params = call.receiveParameters()
            val name = params["name"] ?: throw IllegalArgumentException("El nombre es obligatorio")

            val eventRequest = EventRequest(name, LocalDate.now())
            eventService.updateEvent(id, eventRequest) // Llamamos al servicio para actualizar el evento
            call.respondRedirect("/") // Redirigimos a la lista de eventos
        }

        post("/delete/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("ID inválido")
            eventService.deleteEvent(id) // Llamamos al servicio para eliminar el evento
            call.respondRedirect("/") // Redirigimos a la lista de eventos
        }
    }
}