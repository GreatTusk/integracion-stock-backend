package com.integracion.com.integracion.stock.service.inventory

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetInventoryDto(
    val productId: Int,
    val quantity: Int,
    val minStock: Int,
    val location: String,
    val lastUpdated: LocalDateTime
)

@Serializable
data class PostInventoryDto(
    val productId: Int,
    val quantity: Int,
    val minStock: Int,
    val location: String
)
