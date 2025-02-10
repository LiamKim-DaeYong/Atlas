package com.atlas.shop.domain.order

import com.atlas.shop.common.annotation.DomainEntity
import com.atlas.shop.domain.vo.Money
import com.atlas.shop.util.IdGeneratorFactory

@DomainEntity
class Order(
    val userId: String,
    val totalPrice: Money,
    val status: OrderStatus,
    val orderItems: List<OrderItem>,
    val id: String = IdGeneratorFactory.generate(),
)