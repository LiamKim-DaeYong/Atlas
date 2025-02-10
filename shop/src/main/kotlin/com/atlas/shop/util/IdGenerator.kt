package com.atlas.shop.util

fun interface IdGenerator {
    fun generate(): String
}

object IdGeneratorFactory {
    private val default: IdType = IdType.UUID  // 기본값 설정

    fun getGenerator(type: IdType): IdGenerator = when (type) {
        IdType.UUID -> UUIDGenerator
        IdType.Snowflake -> TODO()
        IdType.Custom -> CustomIdGenerator
    }

    fun generate(type: IdType = default): String = getGenerator(type).generate()
}

sealed class IdType {
    data object UUID : IdType()
    data object Snowflake : IdType()
    data object Custom : IdType()
}

val UUIDGenerator = IdGenerator { java.util.UUID.randomUUID().toString() }
val CustomIdGenerator = IdGenerator { "CUSTOM-${System.currentTimeMillis()}" }