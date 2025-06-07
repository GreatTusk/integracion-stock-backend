package com.integracion.com.integracion.stock.fake

import com.integracion.com.integracion.stock.controller.inventory.inventoryRouting
import com.integracion.com.integracion.stock.controller.product.productCategoryRoute
import com.integracion.com.integracion.stock.controller.product.productRouting
import com.integracion.com.integracion.stock.plugin.configureContentNegotiation
import com.integracion.com.integracion.stock.plugin.configureDocs
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.fakeAppModule() {
    configureContentNegotiation()
    install(Koin) {
        slf4jLogger()
        modules(
            module {
                includes(FakeModule)
            }
        )
    }
    configureDocs()
    productRouting()
    productCategoryRoute()
    inventoryRouting()
}
