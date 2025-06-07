package com.integracion.com.integracion.stock.service.product

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result

interface ProductService {
    suspend fun getAllProducts(): Result<List<ProductDto>, DataError.Remote>
    suspend fun getProductById(productId: Int): Result<ProductDto, DataError.Remote>
    suspend fun createProduct(product: ProductDto): Result<ProductDto, DataError.Remote>
    suspend fun updateProduct(product: ProductUpdateDto): Result<ProductDto, DataError.Remote>
    suspend fun deleteProduct(productId: Int): EmptyResult<DataError.Remote>
}
