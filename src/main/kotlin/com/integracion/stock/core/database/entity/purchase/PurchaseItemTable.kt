package com.integracion.com.integracion.stock.core.database.entity.purchase

import com.integracion.com.integracion.stock.core.database.entity.product.ProductEntity
import com.integracion.com.integracion.stock.core.database.entity.product.ProductTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object PurchaseItemTable : IntIdTable("purchase_item") {
    val purchaseId = reference("purchase_id", PurchaseOrderTable)
    val productId = reference("product_id", ProductTable)
    val quantity = integer("quantity")
    val unitCost = decimal("unit_cost", 10, 2)
}

class PurchaseItemEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PurchaseItemEntity>(PurchaseItemTable)

    var purchaseOrder by PurchaseOrderEntity referencedOn PurchaseItemTable.purchaseId
    var product by ProductEntity referencedOn PurchaseItemTable.productId
    var quantity by PurchaseItemTable.quantity
    var unitCost by PurchaseItemTable.unitCost
}