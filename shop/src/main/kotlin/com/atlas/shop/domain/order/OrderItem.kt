package com.atlas.shop.domain.order

import com.atlas.shop.common.annotation.DomainEntity
import com.atlas.shop.domain.vo.Money

@DomainEntity
class OrderItem(
    val id: Long? = null,
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val price: Money
)