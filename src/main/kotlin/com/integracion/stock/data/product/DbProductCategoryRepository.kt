package com.integracion.com.integracion.stock.data.product

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.core.common.emptyError
import com.integracion.com.integracion.stock.core.database.entity.product.CategoryEntity
import com.integracion.com.integracion.stock.core.database.util.safeSuspendTransaction
import com.integracion.com.integracion.stock.data.product.mapper.toCategory
import com.integracion.com.integracion.stock.domain.product.ProductCategory
import com.integracion.com.integracion.stock.domain.product.ProductCategoryRepository
import com.integracion.com.integracion.stock.service.product.ProductCategoryDto

object DbProductCategoryRepository : ProductCategoryRepository {
    override suspend fun getAllProductCategories(): Result<List<ProductCategory>, DataError.Remote> =
        safeSuspendTransaction {
            CategoryEntity.all().map { it.toCategory() }
        }

    override suspend fun getProductCategoryById(id: Int): Result<ProductCategory, DataError.Remote> =
        safeSuspendTransaction {
            CategoryEntity.findById(id)?.toCategory() ?: emptyError("Not found")
        }

    override suspend fun createProductCategory(categoryDto: ProductCategoryDto): Result<ProductCategory, DataError.Remote> =
        safeSuspendTransaction {
            try {
                CategoryEntity.new {
                    name = categoryDto.name
                    description = categoryDto.description
                }.toCategory()
            } catch (_: Exception) {
                error("Integrity violation")
            }
        }

    override suspend fun updateProductCategory(category: ProductCategory): Result<ProductCategory, DataError.Remote> =
        safeSuspendTransaction {
            CategoryEntity.findByIdAndUpdate(category.id) {
                it.name = category.name
                it.description = category.description
            }?.toCategory() ?: emptyError("Didn't find category")
        }
}