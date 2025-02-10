package com.atlas.shop.domain.order

import com.atlas.shop.common.annotation.DomainEntity
import com.atlas.shop.domain.vo.Money
import com.atlas.shop.util.IdGeneratorFactory

@DomainEntity
class OrderItem(
    val productId: String,
    val quantity: Int,
    val price: Money,
    val id: String = IdGeneratorFactory.generate(),
)