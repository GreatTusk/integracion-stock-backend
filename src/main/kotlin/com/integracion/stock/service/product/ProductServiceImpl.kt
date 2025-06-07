package com.integracion.com.integracion.stock.service.product

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.core.common.map
import com.integracion.com.integracion.stock.domain.product.ProductRepository
import com.integracion.com.integracion.stock.service.product.mapper.toProductDto

class ProductServiceImpl(
    private val productRepository: ProductRepository
) : ProductService {

    override suspend fun getAllProducts(): Result<List<GetProductDto>, DataError.Remote> {
        return productRepository.getAllProducts().map { productList ->
            productList.map { it.toProductDto() }
        }
    }

    override suspend fun getProductById(productId: Int): Result<GetProductDto, DataError.Remote> {
        return productRepository.getProductById(productId).map { it.toProductDto() }
    }

    override suspend fun createProduct(product: CreateProductDto): Result<GetProductDto, DataError.Remote> {
        return productRepository.createProduct(product).map { it.toProductDto() }
    }

    override suspend fun updateProduct(product: GetProductDto): Result<GetProductDto, DataError.Remote> {
        return productRepository.updateProduct(product).map { it.toProductDto() }
    }

    override suspend fun deleteProduct(productId: Int): EmptyResult<DataError.Remote> {
        return productRepository.deleteProduct(productId)
    }
}
