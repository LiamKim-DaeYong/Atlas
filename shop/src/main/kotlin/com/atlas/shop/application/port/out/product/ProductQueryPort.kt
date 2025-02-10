package com.atlas.shop.application.port.out.product

import com.atlas.shop.domain.product.Product

interface ProductQueryPort {
    fun findById(id: String): Product?
    fun findAll(): List<Product>
    fun findByIds(ids: List<String>): List<Product>
}