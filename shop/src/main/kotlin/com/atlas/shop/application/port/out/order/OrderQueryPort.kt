package com.atlas.shop.application.port.out.order

import com.atlas.shop.domain.order.Order

interface OrderQueryPort {
    fun findById(id: String): Order?
    fun findAll(): List<Order>
}