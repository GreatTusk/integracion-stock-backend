package com.integracion.com.integracion.stock.controller.product

import com.integracion.com.integracion.stock.core.common.onEmpty
import com.integracion.com.integracion.stock.core.common.onError
import com.integracion.com.integracion.stock.core.common.onSuccess
import com.integracion.com.integracion.stock.domain.product.ProductCategoryRepository
import com.integracion.com.integracion.stock.service.product.ProductCategoryDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.productCategoryRoute() {
    val productCategoryRepository by inject<ProductCategoryRepository>()

    routing {
        route("/product-category") {
            get {
                productCategoryRepository.getAllProductCategories()
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

            get("/{id}") {
                val id = call.pathParameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid id")

                productCategoryRepository.getProductCategoryById(id)
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
                val category = try {
                    call.receive<ProductCategoryDto>()
                } catch (e: ContentTransformationException) {
                    return@post call.respond(HttpStatusCode.BadRequest, "Malformed category")
                }

                productCategoryRepository.createProductCategory(category)
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
        }
    }

}