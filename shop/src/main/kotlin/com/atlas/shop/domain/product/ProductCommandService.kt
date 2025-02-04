package com.atlas.shop.domain.product

import com.atlas.shop.application.port.`in`.product.ProductCommandUseCase
import com.atlas.shop.application.port.out.product.ProductCommandPort
import com.atlas.shop.domain.vo.Money
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductCommandService(
    private val productCommandPort: ProductCommandPort,
): ProductCommandUseCase {

    override fun create(name: String, price: Money, stock: Int): Product {
        val product = Product(
            name = Product.Name(name),
            price = price,
            stock = Product.Stock(stock),
        )

        return productCommandPort.save(product)
    }

    override fun update(id: Long, name: String?, price: Money?, stock: Int?): Product {
        val product = productCommandPort.findById(id)
            ?: throw EntityNotFoundException("Product with id $id not found")

        val updatedProduct = product.update(name, price, stock)

        return productCommandPort.save(updatedProduct)
    }

    override fun delete(id: Long): Boolean = productCommandPort.delete(id)
}
