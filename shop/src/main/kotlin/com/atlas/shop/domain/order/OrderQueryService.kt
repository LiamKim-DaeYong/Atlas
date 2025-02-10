package com.atlas.shop.domain.order

import com.atlas.shop.application.port.`in`.order.OrderQueryUseCase
import com.atlas.shop.application.port.out.order.OrderQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderQueryService(
    private val orderQueryPort: OrderQueryPort
): OrderQueryUseCase {

    override fun findAll(): List<Order> = orderQueryPort.findAll()

    override fun findById(id: String): Order? = orderQueryPort.findById(id)
}