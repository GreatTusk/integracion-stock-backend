package com.integracion.com.integracion.stock.service.inventory.mapper

import com.integracion.com.integracion.stock.domain.inventory.Inventory
import com.integracion.com.integracion.stock.service.inventory.InventoryDto


internal fun Inventory.toInventoryDto(): InventoryDto {
    return InventoryDto(
        productId = this.product.id,
        quantity = this.quantity,
        minStock = this.minStock,
        location = this.location
    )
}