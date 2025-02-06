import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property

plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.22"
    id("nu.studer.jooq") version "9.0"
}

group = "com.atlas"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.liquibase:liquibase-core")
    jooqGenerator("org.jooq:jooq-meta-extensions-liquibase:${dependencyManagement.importedProperties["jooq.version"]}")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.3.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.0")
    testImplementation("io.kotest:kotest-assertions-core:5.9.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

sourceSets {
    main {
        java {
            srcDir("build/generated/sources/jooq")
        }
    }
}

jooq {
    version.set(dependencyManagement.importedProperties["jooq.version"])

    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    database.apply {
                        name = "org.jooq.meta.extensions.liquibase.LiquibaseDatabase"
                        isOutputSchemaToDefault = true
                        properties = listOf(
                            Property().withKey("rootPath").withValue("$rootDir/src/main/resources"),
                            Property().withKey("scripts").withValue("/db/liquibase/db.changelog-master.yaml"),
                            Property().withKey("defaultNameCase").withValue("lower")
                        )
                    }
                    generate.apply {
                        isGeneratedAnnotation = true
                        isValidationAnnotations = true
                        isSpringAnnotations = true
                        isFluentSetters = true
                        isRecords = true
                        isKotlinNotNullRecordAttributes = true
                    }
                    target.apply {
                        packageName = "com.atlas.shop.jooq"
                        directory = "build/generated/sources/jooq"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}