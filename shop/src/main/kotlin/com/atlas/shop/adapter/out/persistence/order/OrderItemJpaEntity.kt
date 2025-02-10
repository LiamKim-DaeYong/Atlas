package com.atlas.shop.adapter.out.persistence.order

import com.atlas.shop.adapter.out.persistence.product.ProductJpaEntity
import com.atlas.shop.domain.order.OrderItem
import com.atlas.shop.domain.vo.Money
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "order_items")
class OrderItemJpaEntity(
    @Id
    val id: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    val orderJpaEntity: OrderJpaEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val productJpaEntity: ProductJpaEntity,

    @Column(nullable = false)
    val quantity: Int,

    @Column(nullable = false)
    val price: BigDecimal
) {
    fun toDomain(): OrderItem {
        return OrderItem(
            id = id,
            productId = productJpaEntity.id,
            quantity = quantity,
            price = Money(price)
        )
    }

    companion object {
        fun fromDomain(
            orderItem: OrderItem,
            orderJpaEntity: OrderJpaEntity,
            productJpaEntity: ProductJpaEntity
        ): OrderItemJpaEntity {
            return OrderItemJpaEntity(
                id = orderItem.id,
                orderJpaEntity = orderJpaEntity,
                productJpaEntity = productJpaEntity,
                quantity = orderItem.quantity,
                price = orderItem.price.asBigDecimal()
            )
        }
    }
}
