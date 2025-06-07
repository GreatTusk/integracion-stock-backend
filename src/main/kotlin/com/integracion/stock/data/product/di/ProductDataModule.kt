package com.integracion.com.integracion.stock.data.product.di

import com.integracion.com.integracion.stock.data.product.DbProductCategoryRepository
import com.integracion.com.integracion.stock.data.product.DbProductRepository
import com.integracion.com.integracion.stock.domain.product.ProductCategoryRepository
import com.integracion.com.integracion.stock.domain.product.ProductRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val ProductDataModule = module {
    single { DbProductRepository }.bind<ProductRepository>()
    single { DbProductCategoryRepository }.bind<ProductCategoryRepository>()
}