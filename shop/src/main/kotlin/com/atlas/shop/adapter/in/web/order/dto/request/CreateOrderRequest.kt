package com.atlas.shop.adapter.`in`.web.order.dto.request

import java.math.BigDecimal

data class CreateOrderRequest(
    val userId: String,
    val items: List<OrderItemRequest>
) {
    data class OrderItemRequest(
        val productId: String,
        val quantity: Int,
        val price: BigDecimal
    )
}



