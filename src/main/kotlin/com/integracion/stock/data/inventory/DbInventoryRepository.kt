package com.integracion.com.integracion.stock.data.inventory

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.core.common.emptyError
import com.integracion.com.integracion.stock.core.database.entity.product.InventoryEntity
import com.integracion.com.integracion.stock.core.database.entity.product.ProductEntity
import com.integracion.com.integracion.stock.core.database.util.safeSuspendTransaction
import com.integracion.com.integracion.stock.data.inventory.mapper.toInventory
import com.integracion.com.integracion.stock.domain.inventory.Inventory
import com.integracion.com.integracion.stock.domain.inventory.InventoryRepository
import com.integracion.com.integracion.stock.service.inventory.InventoryDto
import com.integracion.com.integracion.stock.service.inventory.InventoryUpdateDto

object DbInventoryRepository : InventoryRepository {

    override suspend fun getAllInventory(): Result<List<Inventory>, DataError.Remote> = safeSuspendTransaction {
        InventoryEntity.all().map { it.toInventory() }
    }

    override suspend fun getInventoryById(inventoryId: Int): Result<Inventory, DataError.Remote> =
        safeSuspendTransaction {
            InventoryEntity.findById(inventoryId)?.toInventory() ?: emptyError("Inventory not found")
        }

    override suspend fun getInventoryByProductId(productId: Int): Result<Inventory, DataError.Remote> =
        safeSuspendTransaction {
            InventoryEntity.find {
                com.integracion.com.integracion.stock.core.database.entity.product.InventoryTable.productId eq productId
            }.firstOrNull()?.toInventory() ?: emptyError("Inventory not found for product")
        }

    override suspend fun createInventory(inventory: InventoryDto): Result<Inventory, DataError.Remote> =
        safeSuspendTransaction {
            val productEntity = ProductEntity.findById(inventory.productId) ?: return@safeSuspendTransaction emptyError(
                "Product not found"
            )

            InventoryEntity.new {
                product = productEntity
                quantity = inventory.quantity
                minStock = inventory.minStock
                location = inventory.location
            }.toInventory()
        }

    override suspend fun updateInventory(inventory: InventoryUpdateDto): Result<Inventory, DataError.Remote> =
        safeSuspendTransaction {
            val inventoryEntity = InventoryEntity.findById(inventory.id)
                ?: return@safeSuspendTransaction emptyError("Inventory not found")

            // If product ID changed, update the reference
            if (inventoryEntity.product.id.value != inventory.productId) {
                val newProductEntity = ProductEntity.findById(inventory.productId)
                    ?: return@safeSuspendTransaction emptyError("New product not found")
                inventoryEntity.product = newProductEntity
            }

            inventoryEntity.quantity = inventory.quantity
            inventoryEntity.minStock = inventory.minStock
            inventoryEntity.location = inventory.location

            inventoryEntity.toInventory()
        }

    override suspend fun deleteInventory(inventoryId: Int): EmptyResult<DataError.Remote> = safeSuspendTransaction {
        InventoryEntity.findById(inventoryId)?.delete() ?: emptyError("Inventory not found")
    }


}
