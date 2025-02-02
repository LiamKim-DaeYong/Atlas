package com.atlas.shop.config

import org.jooq.DSLContext
import org.jooq.codegen.GenerationTool
import org.jooq.impl.DSL
import org.jooq.meta.jaxb.*
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JooqConfig(
    private val dataSource: DataSource,
    private val properties: DataSourceProperties
) {

    @Bean
    fun dslContext(): DSLContext {
        return DSL.using(dataSource, org.jooq.SQLDialect.POSTGRES)
    }

    @Bean
    fun generateJooqSchema(): Boolean {
        val configuration = org.jooq.meta.jaxb.Configuration()
            .withJdbc(
                Jdbc()
                    .withDriver(properties.driverClassName)
                    .withUrl(properties.url)
                    .withUser(properties.username)
                    .withPassword(properties.password)
                    .withProperties(
                        listOf(
                            Property().withKey("user").withValue(properties.username),
                            Property().withKey("password").withValue(properties.password)
                        )
                    )
            )
            .withGenerator(
                Generator()
                    .withDatabase(
                        Database()
                            .withName("org.jooq.meta.postgres.PostgresDatabase")
                            .withInputSchema("public")
                            .withIncludes(".*")
                    )
                    .withGenerate(
                        Generate()
                            .withJavaTimeTypes(true)
                            .withRecords(true)
                    )
                    .withTarget(
                        org.jooq.meta.jaxb.Target()
                            .withPackageName("jooq.dsl")
                            .withDirectory("src/generated/jooq")
                    )
            )

        GenerationTool.generate(configuration)
        return true
    }
}