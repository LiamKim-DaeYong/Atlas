package com.atlas.shop.domain.order

import com.atlas.shop.common.annotation.DomainEntity
import com.atlas.shop.domain.vo.Money

@DomainEntity
class Order(
    val id: Long? = null,
    val userId: Long? = null,
    val status: OrderStatus,
    val totalPrice: Money,
    val orderItems: List<OrderItem> = emptyList()
) {

}