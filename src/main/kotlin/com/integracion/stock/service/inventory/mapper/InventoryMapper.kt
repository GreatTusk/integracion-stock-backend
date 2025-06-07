package com.integracion.com.integracion.stock.service.inventory.mapper

import com.integracion.com.integracion.stock.domain.inventory.Inventory
import com.integracion.com.integracion.stock.service.inventory.GetInventoryDto


internal fun Inventory.toInventoryDto(): GetInventoryDto {
    return GetInventoryDto(
        productId = this.product.id,
        quantity = this.quantity,
        minStock = this.minStock,
        location = this.location,
        lastUpdated = this.lastUpdated
    )
}