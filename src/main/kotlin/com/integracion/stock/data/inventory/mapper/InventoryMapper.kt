package com.integracion.com.integracion.stock.data.inventory.mapper

import com.integracion.com.integracion.stock.core.database.entity.product.InventoryEntity
import com.integracion.com.integracion.stock.data.product.mapper.toProduct
import com.integracion.com.integracion.stock.domain.inventory.Inventory

internal fun InventoryEntity.toInventory(): Inventory {
    return Inventory(
        id = this.id.value,
        product = this.product.toProduct(),
        quantity = this.quantity,
        minStock = this.minStock,
        location = this.location,
        lastUpdated = this.lastUpdated
    )
}
