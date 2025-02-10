package com.atlas.shop.application.port.out.order

import com.atlas.shop.domain.order.Order

interface OrderCommandPort {
    fun findById(id: String): Order?
    fun save(order: Order): Order
}