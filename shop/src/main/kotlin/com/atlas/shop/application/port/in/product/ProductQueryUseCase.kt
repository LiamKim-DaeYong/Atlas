package com.atlas.shop.application.port.`in`.product

import com.atlas.shop.common.annotation.UseCase
import com.atlas.shop.domain.product.Product

@UseCase
interface ProductQueryUseCase {
    fun findAll(): List<Product>
    fun findById(id: String): Product?
}