package com.integracion.com.integracion.stock.domain

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result

interface ProductRepository {
    suspend fun getAllProducts(): Result<List<Product>, DataError.Remote>
    suspend fun getProductById(productId: Int): Result<Product, DataError.Remote>
    suspend fun createProduct(product: Product): Result<Product, DataError.Remote>
    suspend fun updateProduct(product: Product): Result<Product, DataError.Remote>
    suspend fun deleteProduct(productId: Int): EmptyResult<DataError.Remote>
}