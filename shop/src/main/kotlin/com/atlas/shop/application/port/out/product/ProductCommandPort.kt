package com.atlas.shop.application.port.out.product

import com.atlas.shop.domain.product.Product

interface ProductCommandPort {
    fun findById(id: String): Product?
    fun save(product: Product): Product
    fun delete(id: String): Boolean
}