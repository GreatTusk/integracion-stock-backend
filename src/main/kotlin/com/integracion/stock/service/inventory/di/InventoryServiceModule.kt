package com.integracion.com.integracion.stock.service.inventory.di

import com.integracion.com.integracion.stock.data.inventory.di.InventoryDataModule
import com.integracion.com.integracion.stock.service.inventory.InventoryService
import com.integracion.com.integracion.stock.service.inventory.InventoryServiceImpl
import com.integracion.com.integracion.stock.service.product.ProductService
import com.integracion.com.integracion.stock.service.product.ProductServiceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val InventoryServiceModule = module {
    includes(InventoryDataModule)
    singleOf(::ProductServiceImpl).bind<ProductService>()
    singleOf(::InventoryServiceImpl).bind<InventoryService>()
}
