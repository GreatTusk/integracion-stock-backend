package com.integracion.com.integracion.stock.domain.inventory

import com.integracion.com.integracion.stock.domain.product.Product
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Inventory(
    val id: Int,
    val product: Product,
    val quantity: Int,
    val minStock: Int,
    val location: String,
    val lastUpdated: LocalDateTime
)
