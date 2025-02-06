package com.atlas.shop.adapter.`in`.web.product

import com.atlas.shop.adapter.`in`.web.product.dto.request.CreateProductRequest
import com.atlas.shop.adapter.`in`.web.product.dto.request.UpdateProductRequest
import com.atlas.shop.application.port.`in`.product.ProductCommandUseCase
import com.atlas.shop.application.port.`in`.product.ProductQueryUseCase
import com.atlas.shop.common.annotation.WebAdapter
import com.atlas.shop.domain.product.Product
import com.atlas.shop.domain.vo.Money
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
@RequestMapping("/api/v1/product")
class ProductController(
    private val productCommandUseCase: ProductCommandUseCase,
    private val productQueryUseCase: ProductQueryUseCase,
) {

    @GetMapping
    fun getAllProducts(): List<Product> {
        return productQueryUseCase.findAll()
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable("id") id : String): Product? {
        return productQueryUseCase.findById(id)
    }

    @PostMapping
    fun createProduct(@RequestBody request: CreateProductRequest): Product {
        return productCommandUseCase.create(request.name, Money.of(request.price), request.stock)
    }

    @PatchMapping("/{id}")
    fun updateProduct(
        @PathVariable id: String,
        @RequestBody request: UpdateProductRequest
    ): Product {
        return productCommandUseCase.update(
            id = id,
            name = request.name,
            price = request.price?.let { Money.of(it) },
            stock = request.stock
        )
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: String): Boolean {
        return productCommandUseCase.delete(id)
    }
}
