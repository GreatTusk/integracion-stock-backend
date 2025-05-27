package com.integracion.com.integracion.stock.domain

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.service.ProductCategoryDto

interface ProductCategoryRepository {
    suspend fun getAllProductCategories(): Result<List<ProductCategory>, DataError.Remote>
    suspend fun getProductCategoryById(id: Int): Result<ProductCategory, DataError.Remote>
    suspend fun createProductCategory(categoryDto: ProductCategoryDto): Result<ProductCategory, DataError.Remote>
}