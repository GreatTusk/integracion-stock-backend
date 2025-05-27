package com.integracion.com.integracion.stock.core.database.entity.product

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object CategoryTable : IntIdTable("category_table") {
    val name = varchar("name", 50)
    val description = text("description")
}

class CategoryEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<CategoryEntity>(CategoryTable)

    var name by CategoryTable.name
    var description by CategoryTable.description
}