package com.integracion.com.integracion.stock.fake

import com.integracion.com.integracion.stock.domain.inventory.InventoryRepository
import com.integracion.com.integracion.stock.domain.product.ProductCategoryRepository
import com.integracion.com.integracion.stock.domain.product.ProductRepository
import com.integracion.com.integracion.stock.service.inventory.InventoryService
import com.integracion.com.integracion.stock.service.inventory.InventoryServiceImpl
import com.integracion.com.integracion.stock.service.product.ProductService
import com.integracion.com.integracion.stock.service.product.ProductServiceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val FakeModule = module {
    singleOf(::FakeProductCategoryRepository).bind<ProductCategoryRepository>()
    singleOf(::FakeProductRepository).bind<ProductRepository>()
    singleOf(::FakeInventoryRepository).bind<InventoryRepository>()

    singleOf(::InventoryServiceImpl).bind<InventoryService>()
    singleOf(::ProductServiceImpl).bind<ProductService>()
}