package com.integracion.com.integracion.stock.domain.inventory

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.service.inventory.GetInventoryDto
import com.integracion.com.integracion.stock.service.inventory.PostInventoryDto

interface InventoryRepository {
    suspend fun getAllInventory(): Result<List<Inventory>, DataError.Remote>
    suspend fun getInventoryById(inventoryId: Int): Result<Inventory, DataError.Remote>
    suspend fun getInventoryByProductId(productId: Int): Result<Inventory, DataError.Remote>
    suspend fun createInventory(inventory: PostInventoryDto): Result<Inventory, DataError.Remote>
    suspend fun updateInventory(inventory: PostInventoryDto): Result<Inventory, DataError.Remote>
    suspend fun deleteInventory(inventoryId: Int): EmptyResult<DataError.Remote>
}
