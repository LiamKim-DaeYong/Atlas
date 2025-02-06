package com.atlas.shop.config

import org.jooq.DSLContext
import org.jooq.conf.RenderNameCase
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JooqConfig(
    private val dataSource: DataSource,
) {

    @Bean
    fun dslContext(): DSLContext {
        val settings = Settings()
            .withRenderNameCase(RenderNameCase.LOWER);

        return DSL.using(dataSource, org.jooq.SQLDialect.POSTGRES, settings)
    }
}