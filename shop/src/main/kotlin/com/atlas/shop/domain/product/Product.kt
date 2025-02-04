package com.atlas.shop.domain.product

import com.atlas.shop.common.annotation.DomainEntity
import com.atlas.shop.domain.vo.Money

@DomainEntity
class Product(
    val id: Long? = null,
    val name: Name,
    val price: Money,
    val stock: Stock
) {
    fun isAvailable(): Boolean = stock.toInt() > 0

    fun changePrice(newPrice: Money): Product {
        return Product(id, name, newPrice, stock)
    }

    fun reduceStock(quantity: Int): Product {
        return Product(id, name, price, stock.decrease(quantity))
    }

    fun addStock(quantity: Int): Product {
        return Product(id, name, price, stock.increase(quantity))
    }

    fun update(name: String? = null, price: Money? = null, stock: Int? = null): Product {
        return Product(
            id = this.id,
            name = name?.let { Name(it) } ?: this.name,
            price = price ?: this.price,
            stock = stock?.let { Stock(it) } ?: this.stock
        )
    }

    @JvmInline
    value class Name(val value: String) {
        init {
            require(value.isNotBlank()) { "Product name cannot be blank" }
            require(value.length in 2..100) { "Product name must be between 2 and 100 characters" }
        }

        override fun toString() = value
    }

    @JvmInline
    value class Stock(val value: Int) {
        init {
            require(value >= 0) { "Stock quantity cannot be negative" }
        }

        fun decrease(quantity: Int): Stock {
            require(quantity > 0) { "Decrease quantity must be greater than zero." }
            require(value >= quantity) { "Not enough stock available" }
            return Stock(value - quantity)
        }

        fun increase(quantity: Int): Stock {
            require(quantity > 0) { "Increase quantity must be greater than zero." }
            return Stock(value + quantity)
        }

        fun toInt(): Int = value

        override fun toString(): String = value.toString()
    }
}