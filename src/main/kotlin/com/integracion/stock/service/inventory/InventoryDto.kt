package com.integracion.com.integracion.stock.service.inventory

import kotlinx.serialization.Serializable

@Serializable
data class InventoryDto(
    val productId: Int,
    val quantity: Int,
    val minStock: Int,
    val location: String
)

@Serializable
data class InventoryUpdateDto(
    val id: Int,
    val productId: Int,
    val quantity: Int,
    val minStock: Int,
    val location: String
)
