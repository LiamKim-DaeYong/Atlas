package com.atlas.shop.application.port.`in`.order.command

import com.atlas.shop.domain.vo.Money

data class PlaceOrderCommand(
    val userId: String,
    val items: List<PlaceOrderItemCommand>
)

data class PlaceOrderItemCommand(
    val productId: String,
    val quantity: Int,
    val price: Money
)
