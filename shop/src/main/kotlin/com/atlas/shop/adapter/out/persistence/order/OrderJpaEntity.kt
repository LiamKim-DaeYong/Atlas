package com.atlas.shop.adapter.out.persistence.order

import com.atlas.shop.domain.order.Order
import com.atlas.shop.domain.order.OrderStatus
import com.atlas.shop.domain.vo.Money
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "orders")
class OrderJpaEntity(
    @Id
    val id: String,

    @Column(nullable = false)
    val userId: String,

    @Column(nullable = false)
    val totalPrice: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: OrderStatus,

    @OneToMany(mappedBy = "orderJpaEntity", cascade = [CascadeType.ALL])
    val items: MutableList<OrderItemJpaEntity> = mutableListOf()
) {

    fun toDomain(): Order {
        return Order(
            id = id,
            userId = userId,
            totalPrice = Money(totalPrice),
            status = status,
            orderItems = items.map { it.toDomain() }
        )
    }

    companion object {
        fun fromDomain(order: Order): OrderJpaEntity {
            return OrderJpaEntity(
                id = order.id,
                userId = order.userId,
                totalPrice = order.totalPrice.asBigDecimal(),
                status = order.status
            )
        }
    }
}