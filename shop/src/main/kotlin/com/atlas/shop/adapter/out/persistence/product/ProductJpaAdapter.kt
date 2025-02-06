package com.atlas.shop.adapter.out.persistence.product

import com.atlas.shop.application.port.out.product.ProductCommandPort
import com.atlas.shop.common.annotation.PersistenceAdapter
import com.atlas.shop.domain.product.Product

@PersistenceAdapter
class ProductJpaAdapter(
    private val jpaRepository: ProductJpaRepository
) : ProductCommandPort {

    override fun findById(id: String): Product? {
        return jpaRepository.findById(id).map { it.toDomain() }.orElse(null)
    }

    override fun save(product: Product): Product {
        val entity = ProductJpaEntity.fromDomain(product)
        val savedEntity = jpaRepository.save(entity)

        return savedEntity.toDomain()
    }

    override fun delete(id: String): Boolean {
        return if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}