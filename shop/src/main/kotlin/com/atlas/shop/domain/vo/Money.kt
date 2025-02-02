package com.atlas.shop.domain.vo

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Money(private val amount: BigDecimal) {
    init {
        require(amount >= BigDecimal.ZERO) { "Money amount must be non-negative" }
    }

    operator fun plus(other: Money): Money = Money(amount.add(other.amount))
    operator fun minus(other: Money): Money = Money(amount.subtract(other.amount))
    operator fun times(multiplier: BigDecimal): Money = Money(amount.multiply(multiplier))

    fun isGreaterThan(other: Money): Boolean = amount > other.amount
    fun isZero(): Boolean = amount == BigDecimal.ZERO

    fun asBigDecimal(): BigDecimal = amount

    override fun toString(): String = "$amount"

    companion object {
        private const val DEFAULT_SCALE = 2
        private val DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP

        fun of(value: Double): Money =
            Money(BigDecimal(value).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE))

        fun of(value: String): Money =
            Money(BigDecimal(value).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE))

        fun zero(): Money = Money(BigDecimal.ZERO.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE))
    }
}