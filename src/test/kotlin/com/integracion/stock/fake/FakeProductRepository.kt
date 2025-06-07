package com.integracion.com.integracion.stock.fake

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.domain.product.Product
import com.integracion.com.integracion.stock.domain.product.ProductCategory
import com.integracion.com.integracion.stock.domain.product.ProductRepository
import com.integracion.com.integracion.stock.service.product.GetProductDto
import com.integracion.com.integracion.stock.service.product.PostProductDto
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class FakeProductRepository : ProductRepository {
    private val categories = listOf(
        ProductCategory(1, "Electronics", "Electronic devices and gadgets"),
        ProductCategory(2, "Clothing", "Apparel and fashion items"),
        ProductCategory(3, "Books", "Physical and digital books")
    )

    private val products = mutableListOf(
        Product(
            id = 1,
            sku = "PHONE001",
            name = "Smartphone Pro",
            description = "Latest flagship smartphone with advanced features",
            price = 89999, // $899.99 in cents
            cost = 65000,  // $650.00 in cents
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
            category = categories[0]
        ),
        Product(
            id = 2,
            sku = "SHIRT001",
            name = "Cotton T-Shirt",
            description = "Comfortable 100% cotton t-shirt",
            price = 2499,  // $24.99 in cents
            cost = 1200,   // $12.00 in cents
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
            category = categories[1]
        ),
        Product(
            id = 3,
            sku = "BOOK001",
            name = "Programming Guide",
            description = "Comprehensive guide to modern programming",
            price = 4999,  // $49.99 in cents
            cost = 2500,   // $25.00 in cents
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
            category = categories[2]
        ),
        Product(
            id = 4,
            sku = "LAPTOP001",
            name = "Gaming Laptop",
            description = "High-performance laptop for gaming and work",
            price = 149999, // $1499.99 in cents
            cost = 120000,  // $1200.00 in cents
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
            category = categories[0]
        )
    )
    private var nextId = 5

    override suspend fun getAllProducts(): Result<List<Product>, DataError.Remote> {
        return Result.Success(products.toList())
    }

    override suspend fun getProductById(productId: Int): Result<Product, DataError.Remote> {
        val product = products.find { it.id == productId }
        return if (product != null) {
            Result.Success(product)
        } else {
            Result.Error(DataError.Remote.NOT_FOUND)
        }
    }

    override suspend fun createProduct(product: PostProductDto): Result<Product, DataError.Remote> {
        // Find category
        val category = categories.find { it.id == product.categoryId }
            ?: return Result.Error(DataError.Remote.NOT_FOUND)

        val newProduct = Product(
            id = nextId++,
            sku = product.sku,
            name = product.name,
            description = product.description,
            price = product.price,
            cost = product.cost,
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
            category = category
        )
        products.add(newProduct)
        return Result.Success(newProduct)
    }

    override suspend fun updateProduct(product: GetProductDto): Result<Product, DataError.Remote> {
        val index = products.indexOfFirst { it.id == product.id }
        if (index == -1) {
            return Result.Error(DataError.Remote.NOT_FOUND)
        }

        val category = categories.find { it.id == product.categoryId }
            ?: return Result.Error(DataError.Remote.NOT_FOUND)

        val existingProduct = products[index]
        val updatedProduct = Product(
            id = product.id,
            sku = product.sku,
            name = product.name,
            description = product.description,
            price = product.price,
            cost = product.cost,
            createdAt = existingProduct.createdAt,
            category = category
        )
        products[index] = updatedProduct
        return Result.Success(updatedProduct)
    }

    override suspend fun deleteProduct(productId: Int): EmptyResult<DataError.Remote> {
        val removed = products.removeIf { it.id == productId }
        return if (removed) {
            Result.Success(Unit)
        } else {
            Result.Error(DataError.Remote.NOT_FOUND)
        }
    }
}
