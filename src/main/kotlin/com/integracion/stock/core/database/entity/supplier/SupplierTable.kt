package com.integracion.com.integracion.stock.core.database.entity.supplier

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object SupplierTable : IntIdTable("supplier") {
    val name = varchar("name", 50)
    val contactPerson = varchar("contact_person", 50)
    val email = varchar("email", 100).uniqueIndex()
    val phone = varchar("phone", 20)
    val address = text("address")
    val createdAt = datetime("created_at")
}

class SupplierEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<SupplierEntity>(SupplierTable)

    var name by SupplierTable.name
    var contactPerson by SupplierTable.contactPerson
    var email by SupplierTable.email
    var phone by SupplierTable.phone
    var address by SupplierTable.address
    var createdAt by SupplierTable.createdAt
}