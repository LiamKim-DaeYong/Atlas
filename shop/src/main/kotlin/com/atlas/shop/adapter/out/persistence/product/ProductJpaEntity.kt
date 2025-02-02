package com.atlas.shop.adapter.out.persistence.product

import com.atlas.shop.domain.Product
import com.atlas.shop.domain.vo.Money
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "products")
class ProductJpaEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    val name: String,

    @Column(nullable = false)
    val price: BigDecimal,

    @Column(nullable = false)
    val stock: Int
) {
    fun toDomain(): Product {
        return Product(
            id = id,
            name = Product.Name(name),
            price = Money(price),
            stock = Product.Stock(stock)
        )
    }

    companion object {
        fun fromDomain(product: Product): ProductJpaEntity {
            return ProductJpaEntity(
                id = product.id,
                name = product.name.toString(),
                price = product.price.asBigDecimal(),
                stock = product.stock.toInt()
            )
        }
    }
}