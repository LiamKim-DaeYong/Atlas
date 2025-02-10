package com.atlas.shop.adapter.out.persistence.order

import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository : JpaRepository<OrderJpaEntity, String>