package com.integracion.com.integracion.stock.fake

import com.integracion.com.integracion.stock.controller.inventory.inventoryRouting
import com.integracion.com.integracion.stock.controller.product.productCategoryRoute
import com.integracion.com.integracion.stock.controller.product.productRouting
import com.integracion.com.integracion.stock.plugin.configureContentNegotiation
import com.integracion.com.integracion.stock.plugin.configureKoin
import com.integracion.com.integracion.stock.plugin.initializeDatabase
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.fakeAppModule() {
    Database.connect(::connectToEmbeddedDb)

    initializeDatabase()

    configureKoin()
    configureContentNegotiation()
    productRouting()
    productCategoryRoute()
    inventoryRouting()
}

