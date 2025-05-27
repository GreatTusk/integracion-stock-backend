package com.integracion.com.integracion.stock.data.di

import com.integracion.com.integracion.stock.data.DbProductCategoryRepository
import com.integracion.com.integracion.stock.data.DbProductRepository
import com.integracion.com.integracion.stock.domain.ProductCategoryRepository
import com.integracion.com.integracion.stock.domain.ProductRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val DataModule = module {
    single { DbProductRepository }.bind<ProductRepository>()
    single { DbProductCategoryRepository }.bind<ProductCategoryRepository>()
}