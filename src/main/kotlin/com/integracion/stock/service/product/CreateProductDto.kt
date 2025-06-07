package com.integracion.com.integracion.stock.service.product

import kotlinx.serialization.Serializable

@Serializable
data class CreateProductDto(
    val sku: String,
    val name: String,
    val description: String,
    val price: Int,
    val cost: Int,
    val categoryId: Int
)

@Serializable
data class GetProductDto(
    val id: Int,
    val sku: String,
    val name: String,
    val description: String,
    val price: Int,
    val cost: Int,
    val categoryId: Int
)