package com.atlas.shop.application.port.out.product

import com.atlas.shop.domain.Product

interface ProductCommandPort {
    fun save(product: Product): Product
    fun delete(id: Long)
}