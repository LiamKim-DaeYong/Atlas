package com.atlas.shop.application.usecase

import com.atlas.shop.application.port.out.product.ProductCommandPort
import com.atlas.shop.common.annotation.UseCase
import com.atlas.shop.domain.Product
import com.atlas.shop.domain.vo.Money
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@UseCase
interface ProductCommandUseCase {
    fun createProduct(name: String, price: Money, stock: Int): Product
    fun deleteProduct(id: Long)
}

@Service
@Transactional
class ProductCommandUseCaseImpl(
    private val productCommandPort: ProductCommandPort,
): ProductCommandUseCase {

    override fun createProduct(name: String, price: Money, stock: Int): Product {
        val product = Product(
            name = Product.Name(name),
            price = price,
            stock = Product.Stock(stock),
        )

        return productCommandPort.save(product)
    }

    override fun deleteProduct(id: Long) = productCommandPort.delete(id)
}
