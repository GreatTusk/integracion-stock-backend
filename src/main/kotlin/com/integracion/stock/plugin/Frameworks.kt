package com.integracion.com.integracion.stock.plugin

import com.integracion.com.integracion.stock.data.di.DataModule
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(module { includes(DataModule) })
    }
}
