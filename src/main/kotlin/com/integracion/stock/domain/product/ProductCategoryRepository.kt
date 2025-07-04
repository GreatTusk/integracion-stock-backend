package com.integracion.com.integracion.stock.domain.product

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.service.product.ProductCategoryDto

interface ProductCategoryRepository {
    suspend fun getAllProductCategories(): Result<List<ProductCategory>, DataError.Remote>
    suspend fun getProductCategoryById(id: Int): Result<ProductCategory, DataError.Remote>
    suspend fun createProductCategory(categoryDto: ProductCategoryDto): Result<ProductCategory, DataError.Remote>
    suspend fun updateProductCategory(category: ProductCategory): Result<ProductCategory, DataError.Remote>
    suspend fun deleteProductCategoryById(id: Int): EmptyResult<DataError.Remote>
}