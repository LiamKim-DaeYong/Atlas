package com.atlas.shop.application.port.`in`.order

import com.atlas.shop.common.annotation.UseCase
import com.atlas.shop.domain.order.Order

@UseCase
interface OrderQueryUseCase {
    fun findAll(): List<Order>
    fun findById(id: String): Order?
}