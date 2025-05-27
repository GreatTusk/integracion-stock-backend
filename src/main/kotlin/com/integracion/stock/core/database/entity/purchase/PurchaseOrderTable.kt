package com.integracion.com.integracion.stock.core.database.entity.purchase

import com.integracion.com.integracion.stock.core.database.entity.supplier.SupplierEntity
import com.integracion.com.integracion.stock.core.database.entity.supplier.SupplierTable
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object PurchaseOrderTable : IntIdTable("purchase_orders") {
    val supplierId = reference("supplier_id", SupplierTable)
    val orderDate = date("order_date")
    val status = enumeration<PurchaseStatus>("status").default(PurchaseStatus.Pending)
    val total = decimal("total", 12, 2)
//    val createdBy = reference("created_by", UserTable).nullable()
    val createdAt = datetime("created_at").default(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
}

enum class PurchaseStatus {
    Pending, Received, Cancelled
}

class PurchaseOrderEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PurchaseOrderEntity>(PurchaseOrderTable)

    var supplier by SupplierEntity referencedOn PurchaseOrderTable.supplierId
    var orderDate by PurchaseOrderTable.orderDate
    var status by PurchaseOrderTable.status
    var total by PurchaseOrderTable.total
    var createdAt by PurchaseOrderTable.createdAt
}