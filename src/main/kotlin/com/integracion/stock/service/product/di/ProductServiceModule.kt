package com.integracion.com.integracion.stock.service.product.di

import com.integracion.com.integracion.stock.data.product.di.ProductDataModule
import com.integracion.com.integracion.stock.service.product.ProductService
import com.integracion.com.integracion.stock.service.product.ProductServiceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val ProductServiceModule = module {
    includes(ProductDataModule)
    singleOf(::ProductServiceImpl).bind<ProductService>()
}
