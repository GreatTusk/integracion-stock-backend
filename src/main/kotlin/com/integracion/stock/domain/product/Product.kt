package com.integracion.com.integracion.stock.domain.product

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val sku: String,
    val name: String,
    val description: String,
    val price: Int,
    val cost: Int,
    val createdAt: LocalDateTime,
    val category: ProductCategory
)

@Serializable
data class ProductCategory(
    val id: Int,
    val name: String,
    val description: String
)
