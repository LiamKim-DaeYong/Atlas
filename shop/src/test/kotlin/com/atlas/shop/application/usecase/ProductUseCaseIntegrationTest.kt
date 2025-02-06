package com.atlas.shop.application.usecase

import com.atlas.shop.adapter.out.persistence.product.ProductJpaRepository
import com.atlas.shop.application.port.`in`.product.ProductCommandUseCase
import com.atlas.shop.application.port.`in`.product.ProductQueryUseCase
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
                    val product = productCommandUseCase.create("Test Product", Money(BigDecimal("100.00")), 10)

                    productQueryUseCase.findById(product.id)!!.let {
                        it.name.toString() shouldBe "Test Product"
                        it.price.toString() shouldBe "100.00"
                        it.stock.toInt() shouldBe 10
                    }
                }
            }

            context("제품을 삭제하면") {
                it("해당 제품이 더 이상 조회되지 않아야 한다") {
                    val product = productCommandUseCase.create("Product To Delete", Money(BigDecimal("200.00")), 2)

                    productCommandUseCase.delete(product.id)

                    val deletedProduct = productQueryUseCase.findById(product.id)
                    deletedProduct shouldBe null
                }
            }

            context("제품을 조회하면") {
                it("생성된 제품 목록이 올바르게 반환되어야 한다") {
                    productCommandUseCase.create("Product A", Money(BigDecimal("100.00")), 5)
                    productCommandUseCase.create("Product B", Money(BigDecimal("75.00")), 8)

                    val products = productQueryUseCase.findAll()
                    products shouldHaveSize 2
                }

                it("특정 제품 ID를 조회하면 해당 제품이 반환되어야 한다") {
                    // given
                    val newProduct = productCommandUseCase.create("Single Product", Money(BigDecimal("150.00")), 7)

                    // when
                    val foundProduct = productQueryUseCase.findById(newProduct.id)

                    // then
                    foundProduct shouldNotBe null
                    foundProduct?.name.toString() shouldBe "Single Product"
                    foundProduct?.price.toString() shouldBe "150.00"
                    foundProduct?.stock?.toInt() shouldBe 7
                }
            }

            context("제품을 수정하면") {
                it("이름, 가격, 재고가 변경되어야 한다") {
                    // given
                    val product = productCommandUseCase.create("Old Name", Money(BigDecimal("50.00")), 3)

                    // when
                    val updatedProduct = productCommandUseCase.update(
                        id = product.id,
                        name = "Updated Name",
                        price = Money(BigDecimal("60.00")),
                        stock = 5
                    )

                    // then
                    updatedProduct.name.toString() shouldBe "Updated Name"
                    updatedProduct.price.toString() shouldBe "60.00"
                    updatedProduct.stock.toInt() shouldBe 5

                    val foundProduct = productQueryUseCase.findById(product.id)
                    foundProduct shouldNotBe null
                    foundProduct?.name.toString() shouldBe "Updated Name"
                    foundProduct?.price.toString() shouldBe "60.00"
                    foundProduct?.stock?.toInt() shouldBe 5
                }

                it("일부 필드만 수정하면 나머지 값은 유지되어야 한다") {
                    // given
                    val product = productCommandUseCase.create("Partial Update", Money(BigDecimal("80.00")), 10)

                    // when - 이름만 변경
                    val updatedProduct = productCommandUseCase.update(
                        id = product.id,
                        name = "New Name",
                        price = null, // 가격 변경 없음
                        stock = null  // 재고 변경 없음
                    )

                    // then
                    updatedProduct.name.toString() shouldBe "New Name"
                    updatedProduct.price.toString() shouldBe "80.00" // 기존 가격 유지
                    updatedProduct.stock.toInt() shouldBe 10 // 기존 재고 유지

                    val foundProduct = productQueryUseCase.findById(product.id)
                    foundProduct shouldNotBe null
                    foundProduct?.name.toString() shouldBe "New Name"
                    foundProduct?.price.toString() shouldBe "80.00"
                    foundProduct?.stock?.toInt() shouldBe 10
                }
            }
        }
    }
}
