package com.integracion.com.integracion.stock.functional

import com.integracion.com.integracion.stock.annotations.Functional
import com.integracion.com.integracion.stock.fake.fakeAppModule
import com.integracion.com.integracion.stock.service.inventory.GetInventoryDto
import com.integracion.com.integracion.stock.service.inventory.PostInventoryDto
import com.integracion.com.integracion.stock.service.product.GetProductDto
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InventoryTest {

    private fun ApplicationTestBuilder.setup() {
        application {
            fakeAppModule()
        }
    }

    @Functional
    @Test
    fun testGetAllInventory() = testApplication {
        setup()

        client.get("/inventory").apply {
            assertEquals(HttpStatusCode.OK, status)
            val responseBody = bodyAsText()
            assertTrue(responseBody.contains("quantity"), "Response should contain inventory quantity data")
            assertTrue(responseBody.contains("location"), "Response should contain inventory location data")
        }
    }

    @Functional
    @Test
    fun testGetInventoryById() = testApplication {
        setup()

        // First get all inventory items to find a valid ID
        val allInventoryResponse = client.get("/inventory")
        assertEquals(HttpStatusCode.OK, allInventoryResponse.status)

        val inventoryItems = Json.decodeFromString<List<GetInventoryDto>>(allInventoryResponse.bodyAsText())
        assertTrue(inventoryItems.isNotEmpty(), "Inventory list should not be empty")

        // Use the ID of the first inventory item
        val firstInventoryId = inventoryItems.first().productId

        // Test getting a single inventory item
        client.get("/inventory/$firstInventoryId").apply {
            assertEquals(HttpStatusCode.OK, status)
            val responseBody = bodyAsText()
            assertTrue(responseBody.contains("quantity"), "Response should contain inventory quantity")
            assertTrue(responseBody.contains("location"), "Response should contain inventory location")
        }
    }

    @Functional
    @Test
    fun testGetInventoryByProductId() = testApplication {
        setup()

        // First get all products to find a valid product ID
        val allInventoryResponse = client.get("/inventory")
        val inventoryItems = Json.decodeFromString<List<GetInventoryDto>>(allInventoryResponse.bodyAsText())

        // Assuming there's at least one inventory item
        assertTrue(inventoryItems.isNotEmpty(), "Inventory list should not be empty")

        // Use the product ID from the first inventory item
        val productId = inventoryItems.first().productId

        // Test getting inventory by product ID
        client.get("/inventory/product/$productId").apply {
            assertEquals(HttpStatusCode.OK, status)
            val responseBody = bodyAsText()
            assertTrue(responseBody.contains("productId"), "Response should contain product ID")
            assertTrue(responseBody.contains("quantity"), "Response should contain inventory quantity")
        }
    }

    @Functional
    @Test
    fun testGetInventoryByInvalidId() = testApplication {
        setup()

        client.get("/inventory/-1").apply {
            println()
            assertEquals(HttpStatusCode.NotFound, status)
        }
    }

    @Functional
    @Test
    fun testCreateInventory() = testApplication {
        setup()

        // Get all products first to find a valid product ID
        val allProductsResponse = client.get("/products")
        val products = Json.decodeFromString<List<GetProductDto>>(allProductsResponse.bodyAsText())
        assertTrue(products.isNotEmpty(), "Product list should not be empty")

        // Get existing inventory to check which products already have inventory
        val allInventoryResponse = client.get("/inventory")
        val inventoryItems = Json.decodeFromString<List<GetInventoryDto>>(allInventoryResponse.bodyAsText())

        // Get existing product IDs in inventory
        val existingProductIds = inventoryItems.map { it.productId }

        // Find a product not in inventory or use the last product
        val testProductId = products
            .firstOrNull { !existingProductIds.contains(it.id) }?.id
            ?: products.last().id

        val newInventory = PostInventoryDto(
            productId = testProductId,
            quantity = 25,
            minStock = 10,
            location = "Test Location"
        )

        client.post("/inventory") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(newInventory))
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
            val responseBody = bodyAsText()
            assertTrue(responseBody.contains("Test Location"), "Response should contain the new inventory location")
        }
    }

    @Functional
    @Test
    fun testUpdateInventory() = testApplication {
        setup()

        // First get all inventory items to find one to update
        val allInventoryResponse = client.get("/inventory")
        val inventoryItems = Json.decodeFromString<List<GetInventoryDto>>(allInventoryResponse.bodyAsText())
        assertTrue(inventoryItems.isNotEmpty(), "Inventory list should not be empty")

        val firstInventory = inventoryItems.first()

        val updateInventory = PostInventoryDto(
            productId = firstInventory.productId,
            quantity = 50,
            minStock = 15,
            location = "Updated Location"
        )

        client.put("/inventory") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(updateInventory))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val responseBody = bodyAsText()
            assertTrue(
                responseBody.contains("Updated Location"),
                "Response should contain the updated inventory location"
            )
        }
    }

    @Functional
    @Test
    fun testDeleteInventory() = testApplication {
        setup()

        // Get all products first
        val allProductsResponse = client.get("/products")
        val products = Json.decodeFromString<List<GetProductDto>>(allProductsResponse.bodyAsText())
        assertTrue(products.isNotEmpty(), "Product list should not be empty")

        // Get existing inventory to find which products already have inventory
        val allInventoryResponse = client.get("/inventory")
        val inventoryItems = Json.decodeFromString<List<GetInventoryDto>>(allInventoryResponse.bodyAsText())

        // Find an inventory item to delete
        if (inventoryItems.isNotEmpty()) {
            // Use an existing inventory item
            val inventoryToDelete = inventoryItems.first()
            val productIdToDelete = inventoryToDelete.productId

            // Delete the inventory
            client.delete("/inventory/$productIdToDelete").apply {
                assertEquals(HttpStatusCode.NoContent, status)
            }

            // Verify it's deleted
            client.get("/inventory/$productIdToDelete").apply {
                assertEquals(HttpStatusCode.NotFound, status)
            }
        } else {
            // If no inventory items exist, create one first
            // Find a product that doesn't have inventory yet
            val existingProductIds = inventoryItems.map { it.productId }
            val availableProduct = products.firstOrNull { !existingProductIds.contains(it.id) }

            assertTrue(availableProduct != null, "No available products to create inventory for testing deletion")

            // Create new inventory for deletion test
            val newInventory = PostInventoryDto(
                productId = availableProduct.id,
                quantity = 5,
                minStock = 2,
                location = "Deletion Test Location"
            )

            // Create the inventory item
            val creationResponse = client.post("/inventory") {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(newInventory))
            }
            assertEquals(HttpStatusCode.Created, creationResponse.status)

            // Extract the created inventory's product ID
            val createdInventory = Json.decodeFromString<GetInventoryDto>(creationResponse.bodyAsText())
            val productIdToDelete = createdInventory.productId

            // Delete the inventory
            client.delete("/inventory/$productIdToDelete").apply {
                assertEquals(HttpStatusCode.NoContent, status)
            }

            // Verify it's deleted
            client.get("/inventory/$productIdToDelete").apply {
                assertEquals(HttpStatusCode.NotFound, status)
            }
        }
    }
}
