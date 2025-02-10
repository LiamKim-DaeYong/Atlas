package com.atlas.shop.application.port.`in`.order

import com.atlas.shop.application.port.`in`.order.command.CancelOrderCommand
import com.atlas.shop.application.port.`in`.order.command.CompleteOrderCommand
import com.atlas.shop.application.port.`in`.order.command.PayOrderCommand
import com.atlas.shop.application.port.`in`.order.command.PlaceOrderCommand
import com.atlas.shop.application.port.`in`.order.command.ShipOrderCommand
import com.atlas.shop.common.annotation.UseCase
import com.atlas.shop.domain.order.Order

@UseCase
interface OrderCommandUseCase {
    fun placeOrder(command: PlaceOrderCommand): Order
    fun payOrder(command: PayOrderCommand)
    fun cancelOrder(command: CancelOrderCommand)
    fun shipOrder(command: ShipOrderCommand)
    fun completeOrder(command: CompleteOrderCommand)
}

