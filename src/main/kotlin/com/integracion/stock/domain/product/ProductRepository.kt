package com.integracion.com.integracion.stock.domain.product

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.service.product.CreateProductDto
import com.integracion.com.integracion.stock.service.product.GetProductDto

interface ProductRepository {
    suspend fun getAllProducts(): Result<List<Product>, DataError.Remote>
    suspend fun getProductById(productId: Int): Result<Product, DataError.Remote>
    suspend fun createProduct(product: CreateProductDto): Result<Product, DataError.Remote>
    suspend fun updateProduct(product: GetProductDto): Result<Product, DataError.Remote>
    suspend fun deleteProduct(productId: Int): EmptyResult<DataError.Remote>
}