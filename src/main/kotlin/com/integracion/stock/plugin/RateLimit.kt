package com.integracion.com.integracion.stock.plugin

import io.ktor.server.application.*
import io.ktor.server.plugins.ratelimit.*
import kotlin.time.Duration.Companion.seconds

fun Application.configureRateLimit() {
    install(RateLimit) {
        global {
            rateLimiter(limit = 10, refillPeriod = 20.seconds)
        }
    }
}