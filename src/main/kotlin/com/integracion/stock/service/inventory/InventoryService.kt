package com.integracion.com.integracion.stock.service.inventory

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result

interface InventoryService {
    suspend fun getAllInventory(): Result<List<GetInventoryDto>, DataError.Remote>
    suspend fun getInventoryById(inventoryId: Int): Result<GetInventoryDto, DataError.Remote>
    suspend fun getInventoryByProductId(productId: Int): Result<GetInventoryDto, DataError.Remote>
    suspend fun createInventory(inventory: PostInventoryDto): Result<GetInventoryDto, DataError.Remote>
    suspend fun updateInventory(inventory: PostInventoryDto): Result<GetInventoryDto, DataError.Remote>
    suspend fun deleteInventory(inventoryId: Int): EmptyResult<DataError.Remote>
}
