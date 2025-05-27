package com.integracion.com.integracion.stock.domain

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.service.ProductDto
import com.integracion.com.integracion.stock.service.ProductUpdateDto

interface ProductRepository {
    suspend fun getAllProducts(): Result<List<Product>, DataError.Remote>
    suspend fun getProductById(productId: Int): Result<Product, DataError.Remote>
    suspend fun createProduct(product: ProductDto): Result<Product, DataError.Remote>
    suspend fun updateProduct(product: ProductUpdateDto): Result<Product, DataError.Remote>
    suspend fun deleteProduct(productId: Int): EmptyResult<DataError.Remote>
}