package com.integracion

import com.integracion.com.integracion.stock.fake.fakeAppModule
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            fakeAppModule()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.NotFound, status)
        }
    }

}
