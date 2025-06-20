package com.integracion.com.integracion.stock.data.product

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.core.common.emptyError
import com.integracion.com.integracion.stock.core.database.entity.product.CategoryEntity
import com.integracion.com.integracion.stock.core.database.entity.product.ProductEntity
import com.integracion.com.integracion.stock.core.database.util.safeSuspendTransaction
import com.integracion.com.integracion.stock.data.product.mapper.toProduct
import com.integracion.com.integracion.stock.domain.product.Product
import com.integracion.com.integracion.stock.domain.product.ProductRepository
import com.integracion.com.integracion.stock.service.product.PostProductDto
import com.integracion.com.integracion.stock.service.product.GetProductDto

object DbProductRepository : ProductRepository {
    override suspend fun getAllProducts(): Result<List<Product>, DataError.Remote> = safeSuspendTransaction {
        ProductEntity.all().map { it.toProduct() }
    }

    override suspend fun getProductById(productId: Int): Result<Product, DataError.Remote> = safeSuspendTransaction {
        ProductEntity.findById(productId)?.toProduct() ?: emptyError("Not found")
    }

    override suspend fun createProduct(product: PostProductDto): Result<Product, DataError.Remote> = safeSuspendTransaction {
        ProductEntity.new {
            name = product.name
            sku = product.sku
            cost = product.cost
            price = product.price
            description = product.description

            val categoryEntity = CategoryEntity.findById(product.categoryId)

            if (categoryEntity != null) {
                category = categoryEntity
            } else {
                emptyError("Not found")
            }
        }.toProduct()
    }

    override suspend fun updateProduct(product: GetProductDto): Result<Product, DataError.Remote> = safeSuspendTransaction {
        ProductEntity.findByIdAndUpdate(product.id) {
            it.name = product.name
            it.sku = product.sku
            it.cost = product.cost
            it.price = product.price
            it.description = product.description
            if (it.category.id.value != product.categoryId) {
                val categoryEntity = CategoryEntity.findById(product.categoryId)
                categoryEntity?.let { category ->
                    it.category = category
                }
            }

        }?.toProduct() ?: emptyError("Not found")
    }

    override suspend fun deleteProduct(productId: Int): EmptyResult<DataError.Remote> = safeSuspendTransaction {
        ProductEntity.findById(productId)?.delete() ?: emptyError("Not found")
    }
}