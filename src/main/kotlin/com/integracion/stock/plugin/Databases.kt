package com.integracion.com.integracion.stock.plugin

import MigrationUtils
import com.integracion.com.integracion.stock.core.database.entity.customer.CustomerTable
import com.integracion.com.integracion.stock.core.database.entity.product.CategoryTable
import com.integracion.com.integracion.stock.core.database.entity.product.InventoryTable
import com.integracion.com.integracion.stock.core.database.entity.product.ProductTable
import com.integracion.com.integracion.stock.core.database.entity.purchase.PurchaseItemTable
import com.integracion.com.integracion.stock.core.database.entity.purchase.PurchaseOrderTable
import com.integracion.com.integracion.stock.core.database.entity.supplier.SupplierTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import java.sql.DriverManager

fun Application.connectToPostgres(): Connection {
    val url = environment.config.property("postgres.url").getString()
    log.info("Connecting to postgres database at $url")
    val user = environment.config.property("postgres.user").getString()
    val password = environment.config.property("postgres.password").getString()

    return DriverManager.getConnection(url, user, password)
}

fun initializeDatabase() = transaction {
    MigrationUtils.statementsRequiredForDatabaseMigration(
        CustomerTable,
        CategoryTable,
        InventoryTable,
        ProductTable,
        PurchaseItemTable,
        PurchaseOrderTable,
        SupplierTable
    ).forEach(::exec)
}