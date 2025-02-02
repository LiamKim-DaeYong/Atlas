package com.atlas.shop.application.usecase

import com.atlas.shop.application.port.out.product.ProductQueryPort
import com.atlas.shop.common.annotation.UseCase
import com.atlas.shop.domain.Product
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@UseCase
interface ProductQueryUseCase {
    fun getAllProducts(): List<Product>
    fun getProductById(id: Long): Product?
}

@Service
@Transactional(readOnly = true)
class ProductQueryUseCaseImpl(
    private val productQueryPort: ProductQueryPort,
): ProductQueryUseCase {

    override fun getAllProducts(): List<Product> = productQueryPort.findAll()

    override fun getProductById(id: Long): Product? = productQueryPort.findById(id)
}
