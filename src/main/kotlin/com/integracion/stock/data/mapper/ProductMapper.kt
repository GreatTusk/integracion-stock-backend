package com.integracion.com.integracion.stock.data.mapper

import com.integracion.com.integracion.stock.core.database.entity.product.CategoryEntity
import com.integracion.com.integracion.stock.core.database.entity.product.ProductEntity
import com.integracion.com.integracion.stock.domain.Product
import com.integracion.com.integracion.stock.domain.ProductCategory
import com.integracion.com.integracion.stock.domain.ProductCategoryRepository

fun ProductEntity.toProduct() = Product(
    id = id.value,
    sku = sku,
    name = name,
    description = description,
    price = price,
    cost = cost,
    createdAt = createdAt,
    category = category.toCategory()
)


fun CategoryEntity.toCategory() = ProductCategory(
    id = id.value,
    name = name,
    description = description
)