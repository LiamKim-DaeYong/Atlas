package com.atlas.shop.util

object UUIDGenerator : IdGenerator {
    override fun generate(): String = java.util.UUID.randomUUID().toString()
}
