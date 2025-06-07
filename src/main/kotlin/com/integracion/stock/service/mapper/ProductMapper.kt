package com.integracion.com.integracion.stock.service.mapper

import com.integracion.com.integracion.stock.domain.Product
import com.integracion.com.integracion.stock.service.ProductDto

internal fun Product.toProductDto(): ProductDto {
    return ProductDto(
        sku = this.sku,
        name = this.name,
        description = this.description,
        price = this.price,
        cost = this.cost,
        categoryId = this.category.id
    )
}