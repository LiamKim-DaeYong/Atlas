package com.atlas.shop.application.usecase

import com.atlas.shop.adapter.out.persistence.product.ProductJpaRepository
import com.atlas.shop.domain.vo.Money
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
class ProductUseCaseIntegrationTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var productCommandUseCase: ProductCommandUseCase

    @Autowired
    private lateinit var productQueryUseCase: ProductQueryUseCase

    @Autowired
    private lateinit var productJpaRepository: ProductJpaRepository

    init {
        beforeEach {
            productJpaRepository.deleteAll()
        }

        this.describe("Product UseCase") {
            context("제품을 생성하면") {
                it("해당 제품이 저장되어야 한다") {
                    val product = productCommandUseCase.createProduct("Test Product", Money(BigDecimal("100.00")), 10)

                    productQueryUseCase.getProductById(product.id!!)!!.let {
                        it.name.toString() shouldBe "Test Product"
                        it.price.toString() shouldBe "100.00"
                        it.stock.toInt() shouldBe 10
                    }
                }
            }

            context("제품을 삭제하면") {
                it("해당 제품이 더 이상 조회되지 않아야 한다") {
                    val product = productCommandUseCase.createProduct("Product To Delete", Money(BigDecimal("200.00")), 2)

                    productCommandUseCase.deleteProduct(product.id!!)

                    val deletedProduct = productQueryUseCase.getProductById(product.id!!)
                    deletedProduct shouldBe null
                }
            }

            context("제품을 조회하면") {
                it("생성된 제품 목록이 올바르게 반환되어야 한다") {
                    productCommandUseCase.createProduct("Product A", Money(BigDecimal("100.00")), 5)
                    productCommandUseCase.createProduct("Product B", Money(BigDecimal("75.00")), 8)

                    val products = productQueryUseCase.getAllProducts()
                    products shouldHaveSize 2
                }

                it("특정 제품 ID를 조회하면 해당 제품이 반환되어야 한다") {
                    // given
                    val newProduct = productCommandUseCase.createProduct("Single Product", Money(BigDecimal("150.00")), 7)

                    // when
                    val foundProduct = productQueryUseCase.getProductById(newProduct.id!!)

                    // then
                    foundProduct shouldNotBe null
                    foundProduct?.name.toString() shouldBe "Single Product"
                    foundProduct?.price.toString() shouldBe "150.00"
                    foundProduct?.stock?.toInt() shouldBe 7
                }
            }
        }
    }
}
