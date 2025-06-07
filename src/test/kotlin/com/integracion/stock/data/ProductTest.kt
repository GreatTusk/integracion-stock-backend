package com.integracion.com.integracion.stock.data

import com.integracion.com.integracion.stock.fake.fakeAppModule
import com.integracion.com.integracion.stock.service.product.PostProductDto
import com.integracion.com.integracion.stock.service.product.GetProductDto
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductTest {

    private fun ApplicationTestBuilder.setup() {
        application {
            fakeAppModule()
        }

    }

    @Test
    fun testGetAllProducts() = testApplication {
        setup()

        client.get("/products").apply {
            assertEquals(HttpStatusCode.OK, status)
            val responseBody = bodyAsText()
            assertTrue(responseBody.contains("AMD Ryzen 9 5950X"), "Response should contain sample product data")
        }
    }

    @Test
    fun testGetProductById() = testApplication {
        setup()
        // First get all products to find a valid ID
        val allProductsResponse = client.get("/products")
        assertEquals(HttpStatusCode.OK, allProductsResponse.status)

        val products = Json.decodeFromString<List<GetProductDto>>(allProductsResponse.bodyAsText())
        assertTrue(products.isNotEmpty(), "Product list should not be empty")

        // Use the ID of the first product
        val firstProductId = products.first().id

        // Test getting a single product
        client.get("/products/$firstProductId").apply {
            assertEquals(HttpStatusCode.OK, status)
            val responseBody = bodyAsText()
            assertTrue(responseBody.contains("id"), "Response should contain product ID")
            assertTrue(responseBody.contains("name"), "Response should contain product name")
        }
    }

    @Test
    fun testGetProductByInvalidId() = testApplication {
        setup()
        client.get("/products/-1").apply {
            assertEquals(HttpStatusCode.NotFound, status)
        }
    }

    @Test
    fun testCreateProduct() = testApplication {
        setup()
        // Get all categories first to find a valid category ID
        val allProductsResponse = client.get("/products")
        val products = Json.decodeFromString<List<GetProductDto>>(allProductsResponse.bodyAsText())
        assertTrue(products.isNotEmpty(), "Product list should not be empty")

        val categoryId = products.first().categoryId

        val newProduct = PostProductDto(
            sku = "TEST-SKU-001",
            name = "Test Product",
            description = "This is a test product",
            price = 10000,
            cost = 5000,
            categoryId = categoryId
        )

        val requestBody = Json.encodeToString(newProduct)

        client.post("/products") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
            val responseBody = bodyAsText()
            assertTrue(responseBody.contains("Test Product"), "Response should contain the new product name")
        }
    }

    @Test
    fun testUpdateProduct() = testApplication {
        setup()
        // First create a test product to update
        val allProductsResponse = client.get("/products")
        val products = Json.decodeFromString<List<GetProductDto>>(allProductsResponse.bodyAsText())

        val firstProduct = products[0]
        val productId = firstProduct.id
        val categoryId = firstProduct.categoryId

        val updateProduct = GetProductDto(
            id = productId,
            sku = "TEST-SKU-UPD",
            name = "Updated Test Product",
            description = "This is an updated test product",
            price = 15000,
            cost = 7500,
            categoryId = categoryId
        )

        val requestBody = Json.encodeToString(updateProduct)

        client.put("/products") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val responseBody = bodyAsText()
            assertTrue(
                responseBody.contains("Updated Test Product"),
                "Response should contain the updated product name"
            )
        }
    }

    @Test
    fun testDeleteProduct() = testApplication {
        setup()

        // First create a test product to delete
        val allProductsResponse = client.get("/products")
        val products = Json.decodeFromString<List<GetProductDto>>(allProductsResponse.bodyAsText())

        // Get a valid category ID from existing products
        val categoryId = products.first().categoryId

        // Create a new product specifically for deletion
        val testProduct = PostProductDto(
            sku = "TEST-DELETE-SKU",
            name = "Test Product for Deletion",
            description = "This product will be deleted",
            price = 9999,
            cost = 5000,
            categoryId = categoryId
        )

        // Create the product
        val createResponse = client.post("/products") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(testProduct))
        }

        assertEquals(HttpStatusCode.Created, createResponse.status)

        // Get the ID of the created product
        val createdProduct = Json.decodeFromString<GetProductDto>(createResponse.bodyAsText())
        val createdProductId = createdProduct.id

        // Now delete the product
        client.delete("/products/$createdProductId").apply {
            assertEquals(HttpStatusCode.NoContent, status)
        }

        // Verify the product was deleted
        client.get("/products/$createdProductId").apply {
            assertEquals(HttpStatusCode.NotFound, status)
        }
    }
}

