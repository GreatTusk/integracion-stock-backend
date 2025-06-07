package com.integracion.com.integracion.stock.plugin

import com.integracion.com.integracion.stock.service.inventory.di.InventoryServiceModule
import com.integracion.com.integracion.stock.service.product.di.ProductServiceModule
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            module {
                includes(ProductServiceModule, InventoryServiceModule)
            }
        )
    }
}
