package com.integracion.com.integracion.stock.service.product

import kotlinx.serialization.Serializable

@Serializable
data class ProductCategoryDto(
    val name: String,
    val description: String
)

@Serializable
data class ProductCategoryUpdateDto(
    val id: Int,
    val name: String,
    val description: String
)