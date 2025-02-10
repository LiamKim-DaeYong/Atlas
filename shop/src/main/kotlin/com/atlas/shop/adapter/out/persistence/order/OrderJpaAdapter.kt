package com.atlas.shop.adapter.out.persistence.order

import com.atlas.shop.adapter.out.persistence.product.ProductJpaRepository
import com.atlas.shop.application.port.out.order.OrderCommandPort
import com.atlas.shop.common.annotation.PersistenceAdapter
import com.atlas.shop.domain.order.Order

@PersistenceAdapter
class OrderJpaAdapter(
    private val orderJpaRepository: OrderJpaRepository,
    private val productJpaRepository: ProductJpaRepository
) : OrderCommandPort {

    override fun findById(id: String): Order? {
        TODO("Not yet implemented")
    }

    override fun save(order: Order): Order {
        val entity = OrderJpaEntity.fromDomain(order)

        val orderItemEntities = order.orderItems.map { item ->
            val productEntity = productJpaRepository.getReferenceById(item.productId)

            OrderItemJpaEntity.fromDomain(
                orderItem = item,
                orderJpaEntity = entity,
                productJpaEntity = productEntity
            )
        }

        entity.items.addAll(orderItemEntities)
        val savedEntity = orderJpaRepository.save(entity)

        return savedEntity.toDomain()
    }
}