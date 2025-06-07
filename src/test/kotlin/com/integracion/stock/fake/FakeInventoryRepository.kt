package com.integracion.com.integracion.stock.fake

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.domain.inventory.Inventory
import com.integracion.com.integracion.stock.domain.inventory.InventoryRepository
import com.integracion.com.integracion.stock.domain.product.Product
import com.integracion.com.integracion.stock.domain.product.ProductCategory
import com.integracion.com.integracion.stock.domain.product.ProductRepository
import com.integracion.com.integracion.stock.service.inventory.PostInventoryDto
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class FakeInventoryRepository(
    private val productRepository: ProductRepository
) : InventoryRepository {
    private val inventory = mutableListOf(
        Inventory(
            id = 1,
            product = Product(
                id = 1,
                sku = "PHONE001",
                name = "Smartphone Pro",
                description = "Latest flagship smartphone with advanced features",
                price = 89999,
                cost = 65000,
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                category = ProductCategory(1, "Electronics", "Electronic devices and gadgets")
            ),
            quantity = 50,
            minStock = 10,
            location = "Warehouse A",
            lastUpdated = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        ),
        Inventory(
            id = 2,
            product = Product(
                id = 2,
                sku = "SHIRT001",
                name = "Cotton T-Shirt",
                description = "Comfortable 100% cotton t-shirt",
                price = 2499,
                cost = 1200,
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                category = ProductCategory(2, "Clothing", "Apparel and fashion items")
            ),
            quantity = 100,
            minStock = 20,
            location = "Warehouse B",
            lastUpdated = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        ),
        Inventory(
            id = 3,
            product = Product(
                id = 3,
                sku = "BOOK001",
                name = "Programming Guide",
                description = "Comprehensive guide to modern programming",
                price = 4999,
                cost = 2500,
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                category = ProductCategory(3, "Books", "Physical and digital books")
            ),
            quantity = 25,
            minStock = 5,
            location = "Warehouse A",
            lastUpdated = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        ),
        Inventory(
            id = 4,
            product = Product(
                id = 4,
                sku = "LAPTOP001",
                name = "Gaming Laptop",
                description = "High-performance laptop for gaming and work",
                price = 149999,
                cost = 120000,
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                category = ProductCategory(1, "Electronics", "Electronic devices and gadgets")
            ),
            quantity = 15,
            minStock = 3,
            location = "Warehouse C",
            lastUpdated = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        )
    )
    private var nextId = 5

    override suspend fun getAllInventory(): Result<List<Inventory>, DataError.Remote> {
        return Result.Success(inventory.toList())
    }

    override suspend fun getInventoryById(inventoryId: Int): Result<Inventory, DataError.Remote> {
        val item = inventory.find { it.id == inventoryId }
        return if (item != null) {
            Result.Success(item)
        } else {
            Result.Error(DataError.Remote.NOT_FOUND)
        }
    }

    override suspend fun getInventoryByProductId(productId: Int): Result<Inventory, DataError.Remote> {
        val item = inventory.find { it.product.id == productId }
        return if (item != null) {
            Result.Success(item)
        } else {
            Result.Error(DataError.Remote.NOT_FOUND)
        }
    }

    override suspend fun createInventory(inventory: PostInventoryDto): Result<Inventory, DataError.Remote> {
        // First, validate that the product exists
        val productResult = productRepository.getProductById(inventory.productId)
        if (productResult is Result.Error) {
            return Result.Error(DataError.Remote.NOT_FOUND) // Product doesn't exist
        }

        val product = (productResult as Result.Success).data

        // Check if inventory for this product already exists
        val existingInventory = this.inventory.find { it.product.id == inventory.productId }
        if (existingInventory != null) {
            return Result.Error(DataError.Remote.NOT_FOUND)
        }

        val newInventory = Inventory(
            id = nextId++,
            product = product,
            quantity = inventory.quantity,
            minStock = inventory.minStock,
            location = inventory.location,
            lastUpdated = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        )
        this.inventory.add(newInventory)
        return Result.Success(newInventory)
    }

    override suspend fun updateInventory(inventory: PostInventoryDto): Result<Inventory, DataError.Remote> {
        // First, validate that the product exists
        val productResult = productRepository.getProductById(inventory.productId)
        if (productResult is Result.Error) {
            return Result.Error(DataError.Remote.NOT_FOUND) // Product doesn't exist
        }

        val product = (productResult as Result.Success).data

        val index = this.inventory.indexOfFirst { it.product.id == inventory.productId }
        if (index == -1) {
            return Result.Error(DataError.Remote.NOT_FOUND)
        }

        val existingInventory = this.inventory[index]
        val updatedInventory = Inventory(
            id = existingInventory.id,
            product = product,
            quantity = inventory.quantity,
            minStock = inventory.minStock,
            location = inventory.location,
            lastUpdated = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        )
        this.inventory[index] = updatedInventory
        return Result.Success(updatedInventory)
    }

    override suspend fun deleteInventory(inventoryId: Int): EmptyResult<DataError.Remote> {
        val removed = inventory.removeIf { it.id == inventoryId }
        return if (removed) {
            Result.Success(Unit)
        } else {
            Result.Error(DataError.Remote.NOT_FOUND)
        }
    }
}
