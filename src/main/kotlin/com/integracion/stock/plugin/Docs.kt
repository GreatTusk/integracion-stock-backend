package com.integracion.com.integracion.stock.plugin

import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureDocs() {
    routing {
        swaggerUI(path = "openapi")
    }
}
