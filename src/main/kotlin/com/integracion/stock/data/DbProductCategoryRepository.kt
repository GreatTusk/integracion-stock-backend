package com.integracion.com.integracion.stock.data

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.core.common.emptyError
import com.integracion.com.integracion.stock.core.database.entity.product.CategoryEntity
import com.integracion.com.integracion.stock.core.database.util.safeSuspendTransaction
import com.integracion.com.integracion.stock.data.mapper.toCategory
import com.integracion.com.integracion.stock.domain.ProductCategory
import com.integracion.com.integracion.stock.domain.ProductCategoryRepository
import com.integracion.com.integracion.stock.service.ProductCategoryDto

object DbProductCategoryRepository : ProductCategoryRepository {
    override suspend fun getAllProductCategories(): Result<List<ProductCategory>, DataError.Remote> =
        safeSuspendTransaction {
            CategoryEntity.all().map { it.toCategory() }
        }

    override suspend fun getProductCategoryById(id: Int): Result<List<ProductCategory>, DataError.Remote> =
        safeSuspendTransaction {
            CategoryEntity.findById(id)?.toCategory() ?: emptyError("Not found")
        }

    override suspend fun createProductCategory(categoryDto: ProductCategoryDto): Result<ProductCategory, DataError.Remote> =
        safeSuspendTransaction {
            CategoryEntity.new {
                name = categoryDto.name
                description = categoryDto.description
            }.toCategory()
        }
}