package com.atlas.shop.adapter.out.persistence.product

import com.atlas.shop.application.port.out.product.ProductQueryPort
import com.atlas.shop.domain.product.Product
import com.atlas.shop.domain.vo.Money
import com.atlas.shop.jooq.tables.Products
import com.atlas.shop.jooq.tables.records.ProductsRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class ProductJooqAdapter(private val dsl: DSLContext) : ProductQueryPort{

    override fun findById(id: String): Product? {
        return dsl.selectFrom(Products.PRODUCTS)
            .where(Products.PRODUCTS.ID.eq(id))
            .fetchOne()
            ?.let { record -> mapToProduct(record) }
    }

    override fun findAll(): List<Product> {
        return dsl.selectFrom(Products.PRODUCTS)
            .fetch()
            .map { record -> mapToProduct(record) }
    }

    override fun findByIds(ids: List<String>): List<Product> {
        return dsl.selectFrom(Products.PRODUCTS)
            .where(Products.PRODUCTS.ID.`in`(ids))
            .fetch()
            .map { record -> mapToProduct(record) }
    }

    private fun mapToProduct(record: ProductsRecord): Product {
        return Product(
            id = record.id,
            name = Product.Name(record.name),
            price = Money(record.price),
            stock = Product.Stock(record.stock),
        )
    }
}