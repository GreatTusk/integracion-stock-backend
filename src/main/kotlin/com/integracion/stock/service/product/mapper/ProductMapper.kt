package com.integracion.com.integracion.stock.service.product.mapper

import com.integracion.com.integracion.stock.domain.product.Product
import com.integracion.com.integracion.stock.service.product.CreateProductDto
import com.integracion.com.integracion.stock.service.product.GetProductDto

internal fun Product.toProductDto(): GetProductDto {
    return GetProductDto(
        id = this.id,
        sku = this.sku,
        name = this.name,
        description = this.description,
        price = this.price,
        cost = this.cost,
        categoryId = this.category.id
    )
}