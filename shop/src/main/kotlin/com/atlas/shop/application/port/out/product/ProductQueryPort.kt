package com.atlas.shop.application.port.out.product

import com.atlas.shop.domain.Product

interface ProductQueryPort {
    fun findById(id: Long): Product?
    fun findAll(): List<Product>
}