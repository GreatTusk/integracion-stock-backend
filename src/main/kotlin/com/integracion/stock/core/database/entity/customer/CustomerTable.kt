package com.integracion.com.integracion.stock.core.database.entity.customer

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object CustomerTable : IntIdTable("customer") {
    val name = varchar("name", 50)
    val email = varchar("email", 100).uniqueIndex()
    val phone = varchar("phone", 20)
    val address = text("address")
}

class CustomerEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CustomerEntity>(CustomerTable)

    var name by CustomerTable.name
    var email by CustomerTable.email
    var phone by CustomerTable.phone
    var address by CustomerTable.address
}