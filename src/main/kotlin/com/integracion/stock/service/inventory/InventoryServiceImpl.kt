package com.integracion.com.integracion.stock.service.inventory

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyResult
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.core.common.map
import com.integracion.com.integracion.stock.domain.inventory.Inventory
import com.integracion.com.integracion.stock.domain.inventory.InventoryRepository
import com.integracion.com.integracion.stock.service.inventory.mapper.toInventoryDto

internal class InventoryServiceImpl(
    private val inventoryRepository: InventoryRepository
) : InventoryService {

    override suspend fun getAllInventory(): Result<List<InventoryDto>, DataError.Remote> {
        return inventoryRepository.getAllInventory().map { inventoryList ->
            inventoryList.map { it.toInventoryDto() }
        }
    }

    override suspend fun getInventoryById(inventoryId: Int): Result<InventoryDto, DataError.Remote> {
        return inventoryRepository.getInventoryById(inventoryId).map { it.toInventoryDto() }
    }

    override suspend fun getInventoryByProductId(productId: Int): Result<InventoryDto, DataError.Remote> {
        return inventoryRepository.getInventoryByProductId(productId).map { it.toInventoryDto() }
    }

    override suspend fun createInventory(inventory: InventoryDto): Result<InventoryDto, DataError.Remote> {
        return inventoryRepository.createInventory(inventory).map { it.toInventoryDto() }
    }

    override suspend fun updateInventory(inventory: InventoryUpdateDto): Result<InventoryDto, DataError.Remote> {
        return inventoryRepository.updateInventory(inventory).map { it.toInventoryDto() }
    }

    override suspend fun deleteInventory(inventoryId: Int): EmptyResult<DataError.Remote> {
        return inventoryRepository.deleteInventory(inventoryId)
    }
}
