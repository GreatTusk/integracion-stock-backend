package com.integracion.com.integracion.stock.controller

import com.integracion.com.integracion.stock.core.common.onEmpty
import com.integracion.com.integracion.stock.core.common.onError
import com.integracion.com.integracion.stock.core.common.onSuccess
import com.integracion.com.integracion.stock.domain.ProductRepository
import com.integracion.com.integracion.stock.service.ProductDto
import com.integracion.com.integracion.stock.service.ProductUpdateDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.productRouting() {
    val productRepository by inject<ProductRepository>()

    routing {
        route("/products") {
            get {
                productRepository.getAllProducts()
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
                val id = call.pathParameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing id")

                val productId =
                    id.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, "Id is not valid")

                productRepository.getProductById(productId)
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
                val product = try {
                    call.receive<ProductDto>()
                } catch (e: ContentTransformationException) {
                    e.printStackTrace()
                    return@post call.respond(HttpStatusCode.BadRequest, "Product malformed")
                }

                productRepository.createProduct(product)
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
                val product = try {
                    call.receive<ProductUpdateDto>()
                } catch (e: ContentTransformationException) {
                    return@put call.respond(HttpStatusCode.BadRequest, "Product malformed")
                }

                productRepository.updateProduct(product)
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
                val id = call.pathParameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing id")

                val productId =
                    id.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest, "Id is not valid")

                productRepository.deleteProduct(productId)
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