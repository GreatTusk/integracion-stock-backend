package com.integracion.com.integracion.stock.plugin

import MigrationUtils
import com.integracion.com.integracion.stock.core.database.entity.customer.CustomerTable
import com.integracion.com.integracion.stock.core.database.entity.product.CategoryEntity
import com.integracion.com.integracion.stock.core.database.entity.product.CategoryTable
import com.integracion.com.integracion.stock.core.database.entity.product.InventoryEntity
import com.integracion.com.integracion.stock.core.database.entity.product.InventoryTable
import com.integracion.com.integracion.stock.core.database.entity.product.ProductEntity
import com.integracion.com.integracion.stock.core.database.entity.product.ProductTable
import com.integracion.com.integracion.stock.core.database.entity.purchase.PurchaseItemTable
import com.integracion.com.integracion.stock.core.database.entity.purchase.PurchaseOrderTable
import com.integracion.com.integracion.stock.core.database.entity.supplier.SupplierTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.selectAll
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

fun connectToEmbeddedDb(): Connection =
    DriverManager.getConnection(
        /* url = */ "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        /* user = */ "root",
        /* password = */ ""
    )

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

    initializeSampleData()
}


private fun initializeSampleData() = transaction {
    val hasProducts = ProductTable.selectAll().count() > 0

    if (!hasProducts) {
        println("No products found. Creating sample data for computer store...")

        // Create sample categories for a computer store
        val laptopsCategory = CategoryEntity.new {
            name = "Laptops"
            description = "Portable computers including gaming, business, and ultrabooks"
        }

        val componentsCategory = CategoryEntity.new {
            name = "Components"
            description = "Computer parts like CPUs, GPUs, motherboards, and RAM"
        }

        val peripheralsCategory = CategoryEntity.new {
            name = "Peripherals"
            description = "Keyboards, mice, headsets, and other computer accessories"
        }

        val networkingCategory = CategoryEntity.new {
            name = "Networking"
            description = "Routers, switches, access points, and networking cables"
        }

        // Create sample products in Laptops category
        val gamingLaptop = ProductEntity.new {
            name = "Gaming Laptop Pro X"
            sku = "LAP-GAM-001"
            description = "High-performance gaming laptop with RTX 3080, 32GB RAM and 1TB NVMe SSD"
            category = laptopsCategory
            price = 219900
            cost = 179900
        }

        val ultrabook = ProductEntity.new {
            name = "Ultrabook Air 13"
            sku = "LAP-ULT-002"
            description = "Ultra-thin laptop with 16GB RAM, 512GB SSD and 14-hour battery life"
            category = laptopsCategory
            price = 149900
            cost = 119900
        }

        val businessLaptop = ProductEntity.new {
            name = "Business Notebook Pro"
            sku = "LAP-BUS-003"
            description = "Business-grade laptop with security features, 16GB RAM and 512GB SSD"
            category = laptopsCategory
            price = 129900
            cost = 99900
        }

        // Create sample products in Components category
        val graphicsCard = ProductEntity.new {
            name = "GeForce RTX 4070 Ti"
            sku = "COMP-GPU-001"
            description = "High-end graphics card for gaming and content creation"
            category = componentsCategory
            price = 89900
            cost = 69900
        }

        val processor = ProductEntity.new {
            name = "AMD Ryzen 9 5950X"
            sku = "COMP-CPU-002"
            description = "16-core desktop processor for extreme performance"
            category = componentsCategory
            price = 59900
            cost = 42900
        }

        val ramKit = ProductEntity.new {
            name = "32GB DDR5 RAM Kit"
            sku = "COMP-RAM-003"
            description = "High-speed memory kit (2x16GB) 6000MHz CL36"
            category = componentsCategory
            price = 24900
            cost = 18900
        }

        // Create sample products in Peripherals category
        val keyboard = ProductEntity.new {
            name = "Mechanical Gaming Keyboard"
            sku = "PERI-KBD-001"
            description = "RGB mechanical keyboard with hot-swappable switches"
            category = peripheralsCategory
            price = 12900
            cost = 8900
        }

        val mouse = ProductEntity.new {
            name = "Wireless Gaming Mouse"
            sku = "PERI-MOU-002"
            description = "Ultra-light wireless gaming mouse with 20K DPI sensor"
            category = peripheralsCategory
            price = 8900
            cost = 5900
        }

        // Create sample products in Networking category
        val router = ProductEntity.new {
            name = "WiFi 6E Router"
            sku = "NET-RTR-001"
            description = "Tri-band WiFi 6E router with mesh capabilities"
            category = networkingCategory
            price = 29900
            cost = 19900
        }

        val networkSwitch = ProductEntity.new {
            name = "10Gb Network Switch"
            sku = "NET-SWI-002"
            description = "8-port managed 10Gb Ethernet switch for home or office"
            category = networkingCategory
            price = 19900
            cost = 14900
        }

        // Laptop inventory records - store in "Main Showroom"
        InventoryEntity.new {
            product = gamingLaptop
            quantity = 15
            minStock = 5
            location = "Main Showroom"
        }

        InventoryEntity.new {
            product = ultrabook
            quantity = 25
            minStock = 10
            location = "Main Showroom"
        }

        InventoryEntity.new {
            product = businessLaptop
            quantity = 20
            minStock = 8
            location = "Main Showroom"
        }

        // Component inventory records - store in "Components Section"
        InventoryEntity.new {
            product = graphicsCard
            quantity = 12
            minStock = 3
            location = "Components Section"
        }

        InventoryEntity.new {
            product = processor
            quantity = 18
            minStock = 5
            location = "Components Section"
        }

        InventoryEntity.new {
            product = ramKit
            quantity = 35
            minStock = 15
            location = "Components Section"
        }

        // Peripheral inventory records - store in "Accessories Area"
        InventoryEntity.new {
            product = keyboard
            quantity = 50
            minStock = 20
            location = "Accessories Area"
        }

        InventoryEntity.new {
            product = mouse
            quantity = 65
            minStock = 25
            location = "Accessories Area"
        }

        // Networking inventory records - store in "Networking Corner"
        InventoryEntity.new {
            product = router
            quantity = 22
            minStock = 8
            location = "Networking Corner"
        }

        InventoryEntity.new {
            product = networkSwitch
            quantity = 17
            minStock = 6
            location = "Networking Corner"
        }

        println("Successfully created inventory records for all products.")
    }
}