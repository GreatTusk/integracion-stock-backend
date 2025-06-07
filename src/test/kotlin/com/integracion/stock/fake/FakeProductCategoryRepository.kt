package com.integracion.com.integracion.stock.fake

import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.domain.product.ProductCategory
import com.integracion.com.integracion.stock.domain.product.ProductCategoryRepository
import com.integracion.com.integracion.stock.service.product.ProductCategoryDto

class FakeProductCategoryRepository : ProductCategoryRepository {
    private val categories = mutableListOf(
        ProductCategory(1, "Electronics", "Electronic devices and gadgets"),
        ProductCategory(2, "Clothing", "Apparel and fashion items"),
        ProductCategory(3, "Books", "Physical and digital books"),
        ProductCategory(4, "Home & Garden", "Items for home improvement and gardening"),
        ProductCategory(5, "Sports", "Sports equipment and accessories")
    )
    private var nextId = 6

    override suspend fun getAllProductCategories(): Result<List<ProductCategory>, DataError.Remote> {
        return Result.Success(categories.toList())
    }

    override suspend fun getProductCategoryById(id: Int): Result<ProductCategory, DataError.Remote> {
        val category = categories.find { it.id == id }
        return if (category != null) {
            Result.Success(category)
        } else {
            Result.Error(DataError.Remote.NOT_FOUND)
        }
    }

    override suspend fun createProductCategory(categoryDto: ProductCategoryDto): Result<ProductCategory, DataError.Remote> {
        val newCategory = ProductCategory(
            id = nextId++,
            name = categoryDto.name,
            description = categoryDto.description
        )
        categories.add(newCategory)
        return Result.Success(newCategory)
    }
}
