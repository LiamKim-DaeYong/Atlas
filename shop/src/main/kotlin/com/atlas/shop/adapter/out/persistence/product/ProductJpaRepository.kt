package com.atlas.shop.adapter.out.persistence.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<ProductJpaEntity, Long>