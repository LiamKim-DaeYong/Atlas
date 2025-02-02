package com.atlas.shop.adapter.`in`.web

import com.atlas.shop.application.usecase.ProductCommandUseCase
import com.atlas.shop.application.usecase.ProductQueryUseCase
import com.atlas.shop.common.annotation.WebAdapter
import com.atlas.shop.domain.Product
import com.atlas.shop.domain.vo.Money
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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
    fun getAllProducts() : List<Product> {
        return productQueryUseCase.getAllProducts()
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable("id") id : Long) : Product? {
        return productQueryUseCase.getProductById(id)
    }

    @PostMapping
    fun createProduct(@RequestBody request: CreateProductRequest): Product {
        return productCommandUseCase.createProduct(request.name, Money.of(request.price), request.stock)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) {
        productCommandUseCase.deleteProduct(id)
    }
}

data class CreateProductRequest(
    val name: String,
    val price: Double,
    val stock: Int
)