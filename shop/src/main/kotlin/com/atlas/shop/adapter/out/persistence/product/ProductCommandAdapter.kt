package com.atlas.shop.adapter.out.persistence.product

import com.atlas.shop.application.port.out.product.ProductCommandPort
import com.atlas.shop.common.annotation.PersistenceAdapter
import com.atlas.shop.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<ProductJpaEntity, Long>

@PersistenceAdapter
class ProductCommandAdapter(
    private val jpaRepository: ProductJpaRepository
) : ProductCommandPort {

    override fun save(product: Product): Product {
        return jpaRepository.save(ProductJpaEntity.fromDomain(product)).toDomain()
    }

    override fun delete(id: Long) {
        jpaRepository.deleteById(id)
    }
}