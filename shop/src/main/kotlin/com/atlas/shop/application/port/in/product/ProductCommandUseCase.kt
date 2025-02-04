package com.atlas.shop.application.port.`in`.product

import com.atlas.shop.common.annotation.UseCase
import com.atlas.shop.domain.product.Product
import com.atlas.shop.domain.vo.Money

@UseCase
interface ProductCommandUseCase {
    fun create(name: String, price: Money, stock: Int): Product
    fun update(id: Long, name: String?, price: Money?, stock: Int?): Product
    fun delete(id: Long): Boolean
}