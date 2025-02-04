package com.atlas.shop.adapter.out.persistence.product

import com.atlas.shop.application.port.out.product.ProductQueryPort
import com.atlas.shop.domain.product.Product
import com.atlas.shop.domain.vo.Money
import jooq.dsl.tables.Products
import jooq.dsl.tables.records.ProductsRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class ProductJooqAdapter(private val dsl: DSLContext) : ProductQueryPort{

    override fun findById(id: Long): Product? {
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

    private fun mapToProduct(record: ProductsRecord): Product {
        return Product(
            id = record[Products.PRODUCTS.ID],
            name = Product.Name(record[Products.PRODUCTS.NAME]),
            price = Money(record[Products.PRODUCTS.PRICE]),
            stock = Product.Stock(record[Products.PRODUCTS.STOCK]),
        )
    }
}