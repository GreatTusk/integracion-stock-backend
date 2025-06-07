package com.integracion.com.integracion.stock.controller.inventory

import com.integracion.com.integracion.stock.core.common.onEmpty
import com.integracion.com.integracion.stock.core.common.onError
import com.integracion.com.integracion.stock.core.common.onSuccess
import com.integracion.com.integracion.stock.service.inventory.InventoryDto
import com.integracion.com.integracion.stock.service.inventory.InventoryService
import com.integracion.com.integracion.stock.service.inventory.InventoryUpdateDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.inventoryRouting() {
    val inventoryService by inject<InventoryService>()

    routing {
        route("/inventory") {
            get {
                inventoryService.getAllInventory()
                    .onSuccess {
                        call.respond(it)
                    }
                    .onEmpty {
                        call.respond(HttpStatusCode.NotFound)
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
            }

            get("/{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing id")

                val inventoryId =
                    id.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, "Id is not valid")

                inventoryService.getInventoryById(inventoryId)
                    .onSuccess {
                        call.respond(it)
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                    .onEmpty {
                        call.respond(HttpStatusCode.NotFound)
                    }
            }

            get("/product/{productId}") {
                val productId = call.parameters["productId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing product id")

                val parsedProductId =
                    productId.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, "Product id is not valid")

                inventoryService.getInventoryByProductId(parsedProductId)
                    .onSuccess {
                        call.respond(it)
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                    .onEmpty {
                        call.respond(HttpStatusCode.NotFound)
                    }
            }

            post {
                val inventory = try {
                    call.receive<InventoryDto>()
                } catch (e: ContentTransformationException) {
                    e.printStackTrace()
                    return@post call.respond(HttpStatusCode.BadRequest, "Inventory data malformed")
                }

                inventoryService.createInventory(inventory)
                    .onSuccess {
                        call.respond(HttpStatusCode.Created, it)
                    }
                    .onEmpty {
                        call.respond(HttpStatusCode.NotFound)
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
            }

            put {
                val inventory = try {
                    call.receive<InventoryUpdateDto>()
                } catch (e: ContentTransformationException) {
                    return@put call.respond(HttpStatusCode.BadRequest, "Inventory data malformed")
                }

                inventoryService.updateInventory(inventory)
                    .onSuccess {
                        call.respond(HttpStatusCode.OK, it)
                    }
                    .onEmpty {
                        call.respond(HttpStatusCode.NotFound)
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
            }

            delete("/{id}") {
                val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing id")

                val inventoryId =
                    id.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest, "Id is not valid")

                inventoryService.deleteInventory(inventoryId)
                    .onSuccess {
                        call.respond(HttpStatusCode.NoContent)
                    }
                    .onEmpty {
                        call.respond(HttpStatusCode.NotFound)
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
            }
        }
    }
}
