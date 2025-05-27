package com.integracion.com.integracion.stock.data

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.core.common.emptyError
import com.integracion.com.integracion.stock.core.database.entity.product.CategoryEntity
import com.integracion.com.integracion.stock.core.database.entity.product.ProductEntity
import com.integracion.com.integracion.stock.core.database.util.safeSuspendTransaction
import com.integracion.com.integracion.stock.data.mapper.toProduct
import com.integracion.com.integracion.stock.domain.Product
import com.integracion.com.integracion.stock.domain.ProductRepository

object DbProductRepository : ProductRepository {
    override suspend fun getAllProducts(): Result<List<Product>, DataError.Remote> = safeSuspendTransaction {
        ProductEntity.all().map { it.toProduct() }
    }

    override suspend fun getProductById(productId: Int): Result<Product, DataError.Remote> = safeSuspendTransaction {
        ProductEntity.findById(productId)?.toProduct() ?: emptyError("Not found")
    }

    override suspend fun createProduct(product: Product): Result<Product, DataError.Remote> = safeSuspendTransaction {
        ProductEntity.new {
            name = product.name
            sku = product.sku
            cost = product.cost.toBigDecimal()
            price = product.price.toBigDecimal()
            description = product.description

            val categoryExists = CategoryEntity.findById(product.id)

            if (categoryExists == null) {
                category = CategoryEntity.new {
                    name = product.category.name
                    description = product.category.description
                }
            } else {
                category = categoryExists
            }
        }.toProduct()
    }

    override suspend fun updateProduct(product: Product): Result<Product, DataError.Remote> = safeSuspendTransaction {
        ProductEntity.findByIdAndUpdate(product.id) {
            it.name = product.name
            it.sku = product.sku
            it.cost = product.cost.toBigDecimal()
            it.price = product.price.toBigDecimal()
            it.description = product.description
            if (it.category.id.value != product.category.id) {
                val categoryEntity = CategoryEntity.findById(product.category.id)
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