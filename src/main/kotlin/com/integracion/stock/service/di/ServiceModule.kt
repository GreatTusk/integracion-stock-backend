package com.integracion.com.integracion.stock.service.di

import com.integracion.com.integracion.stock.service.ProductService
import com.integracion.com.integracion.stock.service.ProductServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val ServiceModule = module {
    single { ProductServiceImpl(get()) }.bind<ProductService>()
}
