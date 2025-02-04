package com.atlas.shop.adapter.`in`.web.product.dto.request

data class CreateProductRequest(
    val name: String,
    val price: Double,
    val stock: Int
)