package com

import com.plugins.*
import com.route.ticketRoutes
import com.route.venueRoutes
import com.routing.eventRoutes
import com.routing.notificationRoutes
import com.routing.participantRoutes
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    configureFrameworks()       // si lo necesitas, p. ej. logging
    configureSerialization()    // JSON
    DatabaseFactory.init()      // Exposed + H2 en memoria
    configureTemplating()       // Thymeleaf
    routing {
        eventRoutes()
        notificationRoutes()
        participantRoutes()
        ticketRoutes()
        venueRoutes()
    }
}