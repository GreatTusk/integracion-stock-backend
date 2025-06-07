package com.integracion.com.integracion.stock.data.inventory.di

import com.integracion.com.integracion.stock.data.inventory.DbInventoryRepository
import com.integracion.com.integracion.stock.domain.inventory.InventoryRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val InventoryDataModule = module {
    single { DbInventoryRepository }.bind<InventoryRepository>()
}