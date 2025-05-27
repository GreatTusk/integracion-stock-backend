package com.integracion.com.integracion.stock.core.database.entity.product

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object ProductTable : IntIdTable("product") {
    val sku = varchar("sku", 50).uniqueIndex()
    val name = varchar("name", 50)
    val description = text("description")
    val categoryId = reference("id_category", CategoryTable)
    val price = integer("price")
    val cost = integer("cost")
    val createdAt = datetime("created_at").default(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
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
