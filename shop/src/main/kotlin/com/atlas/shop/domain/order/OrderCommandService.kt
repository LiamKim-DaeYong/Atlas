package com.atlas.shop.domain.order

import com.atlas.shop.application.port.`in`.order.OrderCommandUseCase
import com.atlas.shop.application.port.`in`.order.command.CancelOrderCommand
import com.atlas.shop.application.port.`in`.order.command.CompleteOrderCommand
import com.atlas.shop.application.port.`in`.order.command.PayOrderCommand
import com.atlas.shop.application.port.`in`.order.command.PlaceOrderCommand
import com.atlas.shop.application.port.`in`.order.command.ShipOrderCommand
import com.atlas.shop.application.port.out.order.OrderCommandPort
import com.atlas.shop.application.port.out.product.ProductQueryPort
import com.atlas.shop.domain.vo.Money
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderCommandService(
    private val orderCommandPort: OrderCommandPort,
    private val productQueryPort: ProductQueryPort
) : OrderCommandUseCase {

    override fun placeOrder(command: PlaceOrderCommand): Order {
        val productIds = command.items.map { it.productId }
        val products = productQueryPort.findByIds(productIds)

        val missingProductIds = productIds - products.map { it.id }.toSet() // ✅ 존재하지 않는 상품 확인

        if (missingProductIds.isNotEmpty()) {
            throw EntityNotFoundException("Products not found: $missingProductIds")
        }

        val orderItems = command.items.map {
            OrderItem(
                productId = it.productId,
                quantity = it.quantity,
                price = it.price
            )
        }

        val totalPrice = orderItems.fold(Money.zero()) { acc, item ->
            acc + (item.price.times(item.quantity.toBigDecimal()))
        }

        val order = Order(
            userId = command.userId,
            status = OrderStatus.PENDING_PAYMENT,
            totalPrice = totalPrice,
            orderItems = orderItems
        )

        return orderCommandPort.save(order)
    }

    override fun payOrder(command: PayOrderCommand) {
        TODO()
    }

    override fun cancelOrder(command: CancelOrderCommand) {
        TODO()
    }

    override fun shipOrder(command: ShipOrderCommand) {
        TODO()
    }

    override fun completeOrder(command: CompleteOrderCommand) {
        TODO()
    }
}
