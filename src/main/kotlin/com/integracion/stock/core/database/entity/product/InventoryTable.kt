package com.integracion.com.integracion.stock.core.database.entity.product

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object InventoryTable: IntIdTable("inventory") {
    val productId = reference("product_id", ProductTable).uniqueIndex()
    val quantity = integer("quantity").default(0)
    val minStock = integer("min_stock").default(5)
    val location = varchar("location", 50)
    val lastUpdated = datetime("last_updated").default(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
}

class InventoryEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<InventoryEntity>(InventoryTable)

    var product by ProductEntity referencedOn InventoryTable
    var quantity by InventoryTable.quantity
    var minStock by InventoryTable.minStock
    var location by InventoryTable.location
    var lastUpdated by InventoryTable.lastUpdated
}