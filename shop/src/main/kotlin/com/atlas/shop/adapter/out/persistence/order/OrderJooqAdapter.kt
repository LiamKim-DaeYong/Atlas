package com.atlas.shop.adapter.out.persistence.order

import com.atlas.shop.application.port.out.order.OrderQueryPort
import com.atlas.shop.domain.order.Order
import com.atlas.shop.domain.order.OrderItem
import com.atlas.shop.domain.order.OrderStatus
import com.atlas.shop.domain.vo.Money
import com.atlas.shop.jooq.tables.OrderItems
import com.atlas.shop.jooq.tables.Orders
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class OrderJooqAdapter(private val dsl: DSLContext) : OrderQueryPort {

    override fun findById(id: String): Order? {
        val orderRecord = dsl
            .selectFrom(Orders.ORDERS)
            .where(Orders.ORDERS.ID.eq(id))
            .fetchOne()
            ?: return null

        val orderItems = dsl
            .selectFrom(OrderItems.ORDER_ITEMS)
            .where(OrderItems.ORDER_ITEMS.ORDER_ID.eq(id))
            .fetch()
            .map { record ->
                OrderItem(
                    id = record.id,
                    productId = record.productId,
                    quantity = record.quantity,
                    price = Money(record.price)
                )
            }

        return Order(
            id = orderRecord.id,
            userId = orderRecord.userId,
            status = OrderStatus.valueOf(orderRecord.status),
            totalPrice = Money(orderRecord.totalPrice),
            orderItems = orderItems
        )
    }

    override fun findAll(): List<Order> {
        TODO("Not yet implemented")
    }
}