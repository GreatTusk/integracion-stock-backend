package com.integracion

import com.integracion.com.integracion.stock.plugin.*
import io.ktor.server.application.*

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    connectToPostgres()
    configureContentNegotiation()
    configureFrameworks()
    configureHTTP()
    configureRouting()
}
