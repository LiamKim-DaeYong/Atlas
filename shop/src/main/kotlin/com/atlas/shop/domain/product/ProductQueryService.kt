package com.atlas.shop.domain.product

import com.atlas.shop.application.port.`in`.product.ProductQueryUseCase
import com.atlas.shop.application.port.out.product.ProductQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductQueryService(
    private val productQueryPort: ProductQueryPort,
): ProductQueryUseCase {

    override fun findAll(): List<Product> = productQueryPort.findAll()

    override fun findById(id: Long): Product? = productQueryPort.findById(id)
}
