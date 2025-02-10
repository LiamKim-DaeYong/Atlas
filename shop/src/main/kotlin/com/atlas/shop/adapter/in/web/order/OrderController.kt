package com.atlas.shop.adapter.`in`.web.order

import com.atlas.shop.adapter.`in`.web.order.dto.request.CreateOrderRequest
import com.atlas.shop.application.port.`in`.order.OrderCommandUseCase
import com.atlas.shop.application.port.`in`.order.OrderQueryUseCase
import com.atlas.shop. application.port.`in`.order.command.PlaceOrderCommand
import com.atlas.shop.application.port.`in`.order.command.PlaceOrderItemCommand
import com.atlas.shop.common.annotation.WebAdapter
import com.atlas.shop.domain.order.Order
import com.atlas.shop.domain.vo.Money
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    private val orderCommandUseCase: OrderCommandUseCase,
    private val orderQueryUseCase: OrderQueryUseCase,
) {

    @GetMapping
    fun getAllProducts(): List<Order> {
        return orderQueryUseCase.findAll()
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable("id") id : String): Order? {
        return orderQueryUseCase.findById(id)
    }

    @PostMapping
    fun createProduct(@RequestBody request: CreateOrderRequest): Order {
        return orderCommandUseCase.placeOrder(PlaceOrderCommand(
            userId = request.userId,
            items = request.items.map {
                PlaceOrderItemCommand(
                    productId = it.productId,
                    quantity = it.quantity,
                    price = Money(it.price)
                )
            }
        ))
    }
}
