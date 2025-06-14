package com.integracion

import com.integracion.com.integracion.stock.controller.inventory.inventoryRouting
import com.integracion.com.integracion.stock.controller.product.productCategoryRoute
import com.integracion.com.integracion.stock.controller.product.productRouting
import com.integracion.com.integracion.stock.plugin.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    if (environment.config.property("h2.use-h2").getString().toBooleanStrict()) {
        Database.connect(::connectToEmbeddedDb)
    } else {
        Database.connect(::connectToPostgres)
    }

    initializeDatabase()

    configureContentNegotiation()
    configureKoin()
    configureDocs()
    configureRateLimit()

    productRouting()
    productCategoryRoute()
    inventoryRouting()
}
