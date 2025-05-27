package com.integracion.com.integracion.stock.core.database.entity.product

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object ProductTable: IntIdTable("product") {
    val sku = varchar("sku", 50).uniqueIndex()
    val name = varchar("name", 50)
    val description = text("description")
    val categoryId = reference("id_category", CategoryTable)
    val price = decimal("price", 5, 2)
    val cost = decimal("cost", 5, 2)
    val createdAt = datetime("created_at")
}

class ProductEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProductEntity>(ProductTable)

    var sku by ProductTable.sku
    var name by ProductTable.name
    var description by ProductTable.description
    var category by CategoryEntity referencedOn ProductTable
    var price by ProductTable.price
    var cost by ProductTable.cost
    var createdAt by ProductTable.createdAt
}
